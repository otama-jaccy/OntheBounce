var net = require('net');
var readline = require('readline');

var server = net.createServer();

server.on('connection', function (socket) {
  console.log('Connection Start');
  socket.on('data', function (chunk) {
    console.log(chunk.toString());
  });
  socket.on('end', function () {
    console.log('Connection closed');
  });
});

server.on('close', function () {
  console.log('Server Closed');
});

server.listen(8080, '127.0.0.1', function () {
  var addr = server.address();
  console.log(`Listening Start on Server - ${addr.address}:${addr.port}`)
});

var rl = readline.createInterface(process.stdin, process.stdout);

rl.on('SIGINT', function () {
  server.close();
  rl.close();
});
