class Base
  constructor: (@socket) ->
    @$parent = null
    @$children = []
    @_events = []

    if @socket
      @socket.on 'connect', @$socketConnect.bind(this)
      @socket.on 'data', @$socketData.bind(this)
      @socket.on 'timeout', @$socketTimeout.bind(this)
      @socket.on 'end', @$socketEnd.bind(this)
      @socket.on 'error', @$socketError.bind(this)



  $on: (event, fn) ->
    (@_events[event] || (@_events[event] = [])).push(fn)
    return this

  $off: (event, fn) ->
    if !arguments.length
      @_events = {}
      return this

    if arguments.length == 1
      @_events[event] = null
      return this

    events = @_events[event]
    for event, i in events
      if event == fn || event.fn == fn
        events.splice(i, 1)
        break
    return this

  $emit: (event) ->
    events = @_events[event]
    if events
      args = Array.prototype.slice.call(arguments, 1)
      for event in events
        event.apply(this, args)
    return this

  $broadcast: (event) ->
    for child in @$children
      child.$emit.apply(child, arguments)
      child.$broadcast.apply(child, arguments)
    return this

  $dispatch: ->
    parent = @$parent
    while(parent)
      parent.$emit.apply(parent, arguments)
      parent = parent.$parent
    return this

  $socketConnect: ->
  $socketData: (_) ->
  $socketTimeout: ->
  $socketEnd: ->
  $socketError: (_) ->

module.exports = Base

