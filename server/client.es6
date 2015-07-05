var net = require('net');
var readline = require('readline');

var options = {
  host: '127.0.0.1',
  port: 8080
};

var client = net.connect(options);

client.on('error', function (e) {
  console.log(`Connection Failed - ${options.host}:${options.port}`);
  console.error(e.message);
});

client.on('connect', function () {
  console.log(`Connected - ${options.host}:${options.port}`);
});

var rl = readline.createInterface(process.stdin, process.stdout);

rl.on('line', function (cmd) {
  console.log(`You just typed: ${cmd}`);
  client.write(cmd);
});


rl.on('SIGINT', function () {
  console.log(`Connection Closed - ${options.host}:${options.port}`);
  client.end();
  rl.close();
});

client.on('data', function (chunk) {
  process.stdout.write(chunk.toString())
});

client.on('end', function (had_error) {
  console.log(`Connection End - ${options.host}:${options.port}`);
});

client.on('close', function () {
  console.log('Client Closed');
  rl.close()
});

