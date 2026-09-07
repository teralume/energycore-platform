// Local-only regression against the isolated EnergyCore evidence database.
const assert = require('node:assert/strict');
const fs = require('node:fs');
const base = 'http://127.0.0.1:8080/api/v1';
if (!process.argv.includes('--isolated-local-demo')) {
  throw new Error('Requires --isolated-local-demo and the local evidence fixture.');
}
async function request(route, token, body) {
  const response = await fetch(base + route, {
    method: body ? 'POST' : 'GET',
    headers: { 'Content-Type': 'application/json', ...(token ? { Authorization: `Bearer ${token}` } : {}) },
    body: body ? JSON.stringify(body) : undefined,
    signal: AbortSignal.timeout(20000),
  });
  assert.equal(response.status, 200, `${route} returned ${response.status}`);
  return response.json();
}
const cents = value => Math.round(Number(value) * 100);
async function run() {
  // Reserved .test fixture only; no production credentials or real payment provider.
  const auth = await request('/auth/sign-in', null, {
    email: 'evidence@energycore.test', password: 'LocalEvidence2026!',
  });
  const snapshots = [];
  for (let i = 0; i < 2; i++) {
    const data = await request('/energy-readings/dashboard-summary', auth.token);
    const active = new Map(data.activeDeviceDetails.map(device => [device.deviceId, device]));
    const powerByRoom = new Map();
    for (const device of active.values()) {
      powerByRoom.set(device.room, (powerByRoom.get(device.room) || 0) + cents(device.watts));
    }
    assert.equal(cents(data.currentWatts), 137000, 'Demo fixture must have 1370 W active.');
    assert.equal(data.activeDevices, 3);
    assert.equal(data.rooms.reduce((sum, room) => sum + cents(room.watts), 0), cents(data.currentWatts));
    for (const room of data.rooms) {
      assert.equal(cents(room.watts), powerByRoom.get(room.room) || 0);
    }
    for (const device of data.topDevices) {
      assert.equal(cents(device.watts), cents(active.get(device.deviceId)?.watts || 0));
    }
    snapshots.push({ currentWatts: data.currentWatts, todayKilowattHours: data.todayKilowattHours,
      rooms: data.rooms, topDevices: data.topDevices });
  }
  const result = { status: 'PASS', executedAt: new Date().toISOString(), environment: 'isolated-local-demo',
    assertions: ['room power equals sum of active devices', 'room totals equal dashboard power',
      'top device power is not cumulative sample watts', '1370 W and 3 active demo devices'], snapshots };
  const outputIndex = process.argv.indexOf('--output');
  if (outputIndex !== -1) {
    assert.ok(process.argv[outputIndex + 1], '--output needs a local file path');
    fs.writeFileSync(process.argv[outputIndex + 1], JSON.stringify(result, null, 2) + '\n');
  }
  console.log(JSON.stringify(result, null, 2));
}
run().catch(error => { console.error(error.message); process.exitCode = 1; });
