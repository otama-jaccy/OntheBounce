net = require 'net'
readline = require 'readline'

options =
  host: '127.0.0.1'
  port: 8080

client = net.connect(options)

client.on 'error', (e) ->
  console.log "Connectionn Failed - #{options.host}:#{options.port}"
  console.error e.message

client.on 'connect', ->
  console.log "Connected - #{options.host}:#{options.port}"

rl = readline.createInterface process.stdin, process.stdout

rl.on 'line', (cmd) ->
  console.log "You just typed: #{cmd}"
  client.write(cmd)

rl.on 'SIGINT', ->
  console.log "Connection Closed - #{options.host}:#{options.port}"
  client.end()
  rl.close()

client.on 'data', (chunk) ->
  process.stdout.write(chunk.toString())

client.on 'end', (had_error) ->
  console.log "Connection End - #{options.host}:#{options.port}"

client.on 'close', ->
  console.log 'Client Closed'
  rl.close()
