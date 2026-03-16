// parallelexecution.spec.js
const { remote } = require('webdriverio');

describe('Parallel Execution Demo', () => {
  const browsers = [
    { browserName: 'chrome', platformName: 'Windows 11' },
    { browserName: 'firefox', platformName: 'Windows 11' },
    { browserName: 'safari', platformName: 'macOS 14' }
  ];

  browsers.forEach((caps) => {
    it(`should run on ${caps.browserName} - ${caps.platformName}`, async () => {
      const driver = await remote({
        user: process.env.SAUCE_USERNAME,
        key: process.env.SAUCE_ACCESS_KEY,
        capabilities: {
          ...caps,
          browserVersion: 'latest',
          'sauce:options': {
            build: 'Parallel-Execution-Demo',
            name: `Test on ${caps.browserName}`
          }
        }
      });

      await driver.url('https://www.saucedemo.com/');
      const title = await driver.getTitle();
      console.log(`Title on ${caps.browserName}: ${title}`);

      await driver.deleteSession();
    });
  });
});
