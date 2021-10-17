/* eslint-disable */
const path = require("path");
const context = __dirname;

const common = {
  resolve: {
    extensions: [".jsx", ".js"],
    modules: [path.join(context, "src"), path.join(context, "node_modules")],
  },
  node: {
    __dirname: false,
    __filename: false,
  },
  context,
  mode: "production",
  devtool: "none",
};

module.exports = () => [
  {
    entry: path.join(context, "src", "index.js"),
    output: {
      publicPath: "/dist/",
      path: path.join(context, "dist"),
      filename: "view.bundle.js",
    },
    target: "web",
    module: {
      rules: [
        {
          test: /\.jsx?$/,
          exclude: /node_modules/,
          use: ["babel-loader"],
        },
        {
          test: /\.(png|jpg|gif|webp|woff|woff2)$/,
          use: ["file-loader"],
        },
        {
          test: /\.css$/,
          use: ["style-loader", "css-loader"],
        },
      ],
    },
    plugins: [],
    ...common,
  },
];
