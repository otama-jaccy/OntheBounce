Base = require './base'

class Member extends Base
  constructor: (@socket) ->
    super

module.exports = Member