var dgram = require('dgram');
var readline = require('readline');

var options = {
  host: '127.0.0.1',
  port: 8080
};

var client = dgram.createSocket('udp4');

var rl = readline.createInterface(process.stdin, process.stdout);

rl.on('line', function (cmd) {
  console.log(`You just typed: ${cmd}`);
  client.send(cmd, 0, cmd.length, options.port, options.host);
});


rl.on('SIGINT', function () {
  console.log(`Connection Closed - ${options.host}:${options.port}`);
  client.close();
  rl.close();
});
