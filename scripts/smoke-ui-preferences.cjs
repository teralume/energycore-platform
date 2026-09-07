// Integration regression. Requires the isolated local evidence database.
// Creates disposable .test accounts; never contacts a deployed API.
const assert = require('node:assert/strict');
const base = 'http://127.0.0.1:8080/api/v1';
if (!process.argv.includes('--isolated-local-demo')) {
  throw new Error('Run only against the isolated local database with --isolated-local-demo.');
}
const results = [];
async function request(route, method = 'GET', body, token) {
  const response = await fetch(base + route, {
    method,
    headers: {'Content-Type': 'application/json', ...(token ? {Authorization: `Bearer ${token}`} : {})},
    body: body ? JSON.stringify(body) : undefined,
    signal: AbortSignal.timeout(20000),
  });
  const data = await response.json();
  assert.equal(response.status, 200, `${method} ${route}: ${JSON.stringify(data)}`);
  return data;
}
async function account(label) {
  return request('/auth/sign-up', 'POST', {
    fullName: 'Preference Regression Demo',
    email: `preferences-${label}-${Date.now()}@energycore.test`,
    password: 'LocalEvidence2026!',
  });
}
async function run() {
  const first = await account('read');
  const reads = await Promise.all(Array.from({length: 24}, () => request('/users/me/ui-preferences', 'GET', undefined, first.token)));
  assert.equal(new Set(reads.map(row => row.id)).size, 1);
  assert.ok(reads[0].id > 0);
  assert.ok(reads.every(row => row.language === 'en' && row.theme === 'dark'));
  results.push({test: '24 concurrent first GET requests', status: 'PASS', singlePreference: true});

  const second = await account('mixed');
  const mixed = await Promise.all(Array.from({length: 24}, (_, i) => request('/users/me/ui-preferences', i % 3 === 0 ? 'GET' : 'PUT', i % 3 === 0 ? undefined : i % 3 === 1 ? {language: 'pt'} : {theme: 'light'}, second.token)));
  assert.equal(new Set(mixed.map(row => row.id)).size, 1);
  const saved = await request('/users/me/ui-preferences', 'GET', undefined, second.token);
  assert.equal(saved.language, 'pt');
  assert.equal(saved.theme, 'light');
  assert.notEqual(saved.userId, reads[0].userId);
  results.push({test: '24 concurrent first GET/PUT requests', status: 'PASS', singlePreference: true, partialUpdatesPreserved: true});

  for (const language of ['es', 'en', 'pt']) {
    await request('/users/me/ui-preferences', 'PUT', {language}, second.token);
    const persisted = await request('/users/me/ui-preferences', 'GET', undefined, second.token);
    assert.equal(persisted.language, language);
    assert.equal(persisted.theme, 'light');
  }
  const untouched = await request('/users/me/ui-preferences', 'GET', undefined, first.token);
  assert.equal(untouched.language, 'en');
  assert.equal(untouched.theme, 'dark');
  results.push({test: 'ES/EN/PT persistence and account isolation', status: 'PASS'});
  console.log(JSON.stringify({status: 'PASS', executedAt: new Date().toISOString(), environment: 'real Spring Boot + isolated local PostgreSQL', results}, null, 2));
}
run().catch(error => { console.error(JSON.stringify({status: 'FAIL', message: error.message, results})); process.exitCode = 1; });
