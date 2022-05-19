const path = require('path')
const express = require('express')
app = module.exports = express();

app.use('/api/v1', require('./api/v1'));
app.use('/api/v2', require('./api/v2'));

app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));

app.get('/', function(req, res) {
  res.render('pages/index');
});

if (!module.parent) {
  app.listen(3000);
  console.log('Express started on port 3000');
}
