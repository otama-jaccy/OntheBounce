gulp = require 'gulp'
coffee = require 'gulp-coffee'
babel = require 'gulp-babel'


gulp.task 'default', ->
  gulp.src('./*.es6')
    .pipe babel()
    .pipe gulp.dest('js')

gulp.task 'watch', ->
  gulp.watch('./*.es6', ['default'])
