const express = require("express");
const path = require("path");

const app = express();
const PORT = 8080;

app.use("/dist", express.static(path.join(__dirname, "dist")));

const staticWwwRoot = path.join(__dirname, "dist", "index.html");

app.get("*", (_request, response) => {
  response.sendFile(staticWwwRoot);
});

app.listen(PORT, () => {
  console.log(`Listening @:${PORT}`);
});
