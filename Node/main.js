//Node.js File processing the incoming json data and sending osc messages to supercollider
//required libraries
var http = require("http")
var osc = require("osc")
var string = require("string")

//variables
var recvPort = 57110;
var sendPort = 57120;
var ipAdress = "127.0.0.1";
var httpPort = 8082;

//setting up OSC
var udpPort = new osc.UDPPort({
	localAddress: ipAddress,
	localPort: recvPort
});
udpPort.open();

function createOSCArgs(args){
	var json = args;
	var array = []; 
	//console.log("creating args");
	delete json.eventName;
	//console.log(json);
	for (var key in json){
		if (json.hasOwnProperty(key)){
			array.push(json[key]);
		}
	}
	//console.log("Args: " + array);
	return array;
};



//handling of incoming http requests
http.createServer(function (request, response){
	response.writeHead(200, {'Content-Type': 'text/plain'});
	//turning the incoming message into a string and getting rid of the 
	//'/' in the beginning, so it can be parsed to json
	message = string(request.url).chompLeft('/');

	//parsing the message to a json object
	messageObj = JSON.parse(message);s
	//console.log(messageObj.EventName);
	var oscAddress = string(messageObj.eventName).ensureLeft('/')
	var appendix = createOSCArgs(messageObj);

	//sending the osc message
	console.log("Sending: " + appendix + " to: " + address);
	udpPort.send({
		address: address,
		args: appendix
	}, ipAdress, sendPort);

   // Send the response body as "Hello World"
   response.end('Hello World\n');
}).listen(8082)

console.log("Server running")