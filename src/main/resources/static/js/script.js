                var client = null;
                // not a secure way to handle this, but works as a demo
                var username = /*[[${username}]]*/ "jack bauer";
                var channel = /*[[${channel}]]*/ "default";
                var time = "123";
                // messages defined in websocket config
                client = Stomp.over(new SockJS('/register'));
                client.connect({}, function (frame) {
                    
                    
                    var subscribeToi = '/users';
                    client.subscribe(subscribeToi, function(response){
                       displayUsers(JSON.parse(response.body));
                    });
                    
                    var subscribeTo = '/channel/' + channel;
                    client.subscribe(subscribeTo, function (response) {
                        displayMessage(JSON.parse(response.body));
                    });
                    
                });
                

                // utility functions for displaying messages
                function send() {
                    client.send("/ws/messages", {}, JSON.stringify({'username': username, 'channel': channel, 'content': document.getElementById('message').value, 'time':time}));
                    document.getElementById('message').value = "";
                }
                function init(){
                    
                    client.send("/ws/getusers");

                }
               
                function displayMessage(message) {
                    var paragraph = document.createElement("p");
                    var textNode = document.createTextNode(message.time + ': ' + message.username + ': ' + message.content);
                    paragraph.appendChild(textNode);
                    document.getElementById("messages").appendChild(paragraph);
                    var objDiv = document.getElementById("messages");
                    objDiv.scrollTop = objDiv.scrollHeight;
                }
                
                function displayUsers(users){
                    $("#userbox").empty();
                    $.each(users, function(i, user) {
                        $("<p/>").text(user).appendTo("#userbox");
                    });
                    
                }

                $(document).keypress(function(e) {
                    if(e.which == 13) {
                        send();
                    }
                });

                // does not work in opera :/ -- this is also triggered
                // on some old IEs when clicking anchor links

                window.addEventListener("beforeunload", function (e) {
                     window.alert("mata")
                    client.send("/ws/close", username);
                    client.disconnect();
                    client.close();

                    (e || window.event).returnValue = null;
                    return null;
                  });

                
                setTimeout(function(){
                    init();
                },500);
                
                
