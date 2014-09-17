var resultArray = [];
var indexes = request.headers.get('adapterIndexes');
for (var i = 0; i < indexes.length; i++) 
	resultArray.push('bean:adapter' + indexes[i]);
result = resultArray.join(',');