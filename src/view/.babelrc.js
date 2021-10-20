/* eslint-disable */
const path = require("path");
const fs = require("fs");
const _fromPairs = require("lodash/fromPairs");

module.exports = (api) => {
  api.cache(true);

  const srcRoot = path.join(__dirname, "src");
  const alias = _fromPairs(
    fs
      .readdirSync(srcRoot)
      .map((sub) => [
        sub,
        `.${path.posix.sep}${path
          .relative(
            __dirname,
            path.posix.join(srcRoot, sub).split(path.sep).join(path.posix.sep)
          )
          .split(path.sep)
          .join(path.posix.sep)}`,
      ])
  );

  return {
    presets: ["@babel/preset-env", "@babel/preset-react"],
    plugins: [
      [
        "module-resolver",
        {
          alias,
          root: [srcRoot],
        },
      ],
      "@babel/plugin-transform-runtime",
      "@babel/plugin-proposal-class-properties",
      "@babel/plugin-proposal-nullish-coalescing-operator",
      "@babel/plugin-proposal-optional-chaining",
      "@babel/plugin-transform-async-to-generator",
    ],
  };
};
