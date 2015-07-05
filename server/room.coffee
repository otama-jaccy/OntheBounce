Base = require './base'

class Room extends Base
  constructor: ->
    super
    @members = []
    @$children = @members

module.exports = Room