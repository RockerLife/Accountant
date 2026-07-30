const assert = require('node:assert/strict');
const fs = require('node:fs');
const path = require('node:path');
const vm = require('node:vm');

const source = fs.readFileSync(path.resolve(__dirname, '../../web/script/lawyers.js'), 'utf8');
const start = source.indexOf('function getSelectedPolicyKeyAccountManagement');
const end = source.indexOf('function checkUnCheckPaymentDiv', start);
assert.ok(start >= 0 && end > start, 'account-management selection functions were not found');

const elements = {
	SearchlistSize: { value: 3 },
	AccountID_0: { checked: true, value: '101' },
	AccountID_1: { checked: false, value: '202' },
	AccountID_2: { checked: true, value: '303' },
	strPolicyKey: { value: '' },
	activityAccountNames: { innerHTML: '' }
};
const context = { document: { getElementById: id => elements[id] || null } };

vm.runInNewContext(source.slice(start, end) + ';getSelectedPolicyKeyAccountManagement();', context);
assert.equal(elements.strPolicyKey.value, '101,303');
assert.equal(elements.activityAccountNames.innerHTML, '101, 303');

context.selectAllAccountManagement(true);
assert.equal(elements.strPolicyKey.value, '101,202,303');
context.selectAllAccountManagement(false);
assert.equal(elements.strPolicyKey.value, '');
