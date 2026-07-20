const assert = require('node:assert/strict');
const fs = require('node:fs');
const path = require('node:path');

const source = fs.readFileSync(path.resolve(__dirname, '../../web/script/lawyers.js'), 'utf8');
const start = source.indexOf('function isCurrentOrPreviousYear');
const end = source.indexOf('\n}', start) + 2;
assert.ok(start >= 0 && end > start, 'year validator was not found');

const validate = Function(source.slice(start, end) + '; return isCurrentOrPreviousYear;')();
assert.equal(validate('2025', 2026), true);
assert.equal(validate('2026', 2026), true);
assert.equal(validate('2024', 2026), false);
assert.equal(validate('2027', 2026), false);
