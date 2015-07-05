net = require 'net'
readline = require 'readline'

server = net.createServer()

server.on 'connection', (socket) ->
  console.log 'Connection Start'

server.on 'close', ->
  console.log 'Server Closed'

server.listen 8080, '127.0.0.1', ->
  addr = server.address()
  console.log "Listening Start on Server - #{addr.address}:#{addr.port}"

rl = readline.createInterface process.stdin, process.stdout

rl.on 'SIGINT', ->
  server.close()
  rl.close()