var stompClient = null;

function connect(callback) {
    var socket = new SockJS('/ordering/calcApp');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/showResult', function(calResult){
            showResult(JSON.parse(calResult.body)['success'], callback);
        });
    });
}
function disconnect() {
    stompClient.disconnect();
    console.log("Disconnected");
}
function changeWebSocket() {
    stompClient.send("/add/calcApp", {}, JSON.stringify({ 'change': 'change'}));
}
function showResult(success, callback) {
    if(success && typeof callback != "undefined"){
        callback();
    }
}