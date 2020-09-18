function getTimestamp(requestParams, context, ee, next){
  context.vars['timestamp'] = Date.now()
  return next();
}

module.exports = {
    getTimestamp: getTimestamp
}
