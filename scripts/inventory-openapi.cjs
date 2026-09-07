const fs = require('node:fs');
const path = require('node:path');

const projectRoot = path.resolve(__dirname, '..');
const sourceRoot = path.join(projectRoot, 'src', 'main', 'java');
const outputPath = path.join(projectRoot, 'docs', 'api-endpoints.md');

const methodByAnnotation = {
  GetMapping: 'GET',
  PostMapping: 'POST',
  PutMapping: 'PUT',
  PatchMapping: 'PATCH',
  DeleteMapping: 'DELETE',
};

function walk(directory) {
  return fs.readdirSync(directory, {withFileTypes: true}).flatMap((entry) => {
    const target = path.join(directory, entry.name);
    return entry.isDirectory() ? walk(target) : [target];
  });
}

function annotationPaths(argument = '') {
  const values = [...argument.matchAll(/"([^"]*)"/g)].map((match) => match[1]);
  return values.length ? values : [''];
}

function normalizeRoute(base, child) {
  const route = `/${[base, child].join('/').replaceAll('\\', '/').split('/').filter(Boolean).join('/')}`;
  return route === '/' ? '/' : route.replace(/\/$/, '');
}

const endpoints = [];
for (const file of walk(sourceRoot).filter((candidate) => candidate.endsWith('Controller.java'))) {
  const source = fs.readFileSync(file, 'utf8');
  const className = path.basename(file, '.java');
  const classDeclaration = source.indexOf(`class ${className}`);
  const beforeClass = classDeclaration >= 0 ? source.slice(0, classDeclaration) : source;
  const baseMatch = [...beforeClass.matchAll(/@RequestMapping\s*\(([^)]*)\)/gs)].at(-1);
  const bases = baseMatch ? annotationPaths(baseMatch[1]) : [''];
  const mappingPattern = /@(GetMapping|PostMapping|PutMapping|PatchMapping|DeleteMapping)\s*(?:\(([^)]*)\))?/gs;
  for (const match of source.matchAll(mappingPattern)) {
    const annotation = match[1];
    const children = annotationPaths(match[2]);
    for (const base of bases) {
      for (const child of children) {
        endpoints.push({
          method: methodByAnnotation[annotation],
          route: normalizeRoute(base, child),
          controller: className,
          source: path.relative(projectRoot, file).replaceAll('\\', '/'),
        });
      }
    }
  }
}

endpoints.sort((left, right) => left.route.localeCompare(right.route) || left.method.localeCompare(right.method));
const generatedAt = new Date().toISOString();
const rows = endpoints.map(({method, route, controller, source}) =>
  `| ${method} | \`${route}\` | ${controller} | \`${source}\` |`,
);
const document = [
  '# EnergyCore REST API endpoint inventory',
  '',
  `Generated from Spring MVC controller annotations at ${generatedAt}.`,
  '',
  `Total documented operations: **${endpoints.length}**.`,
  '',
  'Runtime OpenAPI sources:',
  '',
  '- Swagger UI: `/swagger-ui.html`',
  '- OpenAPI JSON: `/v3/api-docs`',
  '',
  '| Method | Route | Controller | Source |',
  '|:--|:--|:--|:--|',
  ...rows,
  '',
  '> This inventory proves the routes declared in source. A successful runtime request to `/v3/api-docs` is still required before claiming the deployed API contract is operational.',
  '',
].join('\n');

fs.mkdirSync(path.dirname(outputPath), {recursive: true});
fs.writeFileSync(outputPath, document, 'utf8');
console.log(`Wrote ${endpoints.length} operations to ${outputPath}`);
