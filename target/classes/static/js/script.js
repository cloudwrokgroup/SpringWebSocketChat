                var client = null;
                // not a secure way to handle this, but works as a demo
                //var username = /*[[${username}]]*/ document.getElementById("username").innerHTML;
                var username = /*[[${username}]]*/ $("#username").html();
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
                    if($("#file").val()!= ''){
                        window.alert("ON!");
                    }
                    
                    client.send("/ws/messages", {}, JSON.stringify({'username': username, 'channel': channel, 'content': document.getElementById('message').value, 'time':time}));
                    document.getElementById('message').value = "";
                }
                function init(){
                    
                    client.send("/ws/getusers");

                }
                function linkify(message){
                    message = message.replace(/(www\..+?)(\s|$)/g, function(text, link) {
                       return '<a href="http://'+ link +'">'+ link +'</a>';
                        })
                    return message;    
                }
                
                function buildMessage(message){
                    var paragraph = document.createElement("p");
                     var textNode = document.createTextNode(message.time + ': ' + message.username + ': ');
                    paragraph.appendChild(textNode);      
                    
                    var msgParts = message.content.split(" ");
                    
                    $.each(msgParts,function(i, word){
                        if(word.indexOf("www.") !== -1){
                            var a = document.createElement('a');
                            var linkText = document.createTextNode(word);
                            a.appendChild(linkText);
                            a.href = word;
                            a.target = 'blank';
                            paragraph.appendChild(a);
                        }else{
                            var tNode = document.createTextNode(word+" ");
                            paragraph.appendChild(tNode);
                        }
                    });
                     
                    return paragraph; 
                }
                

                function displayMessage(message) {
                    
                    //var paragraph = document.createElement("p");
                    
                    //var textNode = document.createTextNode(message.time + ': ' + message.username + ': ' + message);
                    
                    var paragraph = buildMessage(message);
                    
                    //paragraph.appendChild(textNode);
                    document.getElementById("messages").appendChild(paragraph);
                    var objDiv = document.getElementById("messages");
                    objDiv.scrollTop = objDiv.scrollHeight;
                }
                
                function displayUsers(users){
                    $("#userbox").empty();
                    $.each(users, function(i, user) {
                        if(user===username){
                            $("<b/>").text(user).appendTo("#userbox");
                        }else{
                            $("<p/>").text(user).appendTo("#userbox");
                        }
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
                
                

                


                
                
