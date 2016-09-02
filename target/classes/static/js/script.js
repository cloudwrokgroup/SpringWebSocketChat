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
                

                function send() {
                    if(document.getElementById('message').value.length>0){
                        var imgName = 'default';

                        client.send("/ws/messages", {}, JSON.stringify({'username': username, 'channel': channel, 'content': document.getElementById('message').value, 'time':time,'image':imgName}));
                        document.getElementById('message').value = "";
                    }
                }
                
                document.getElementById("file").onchange = function() {
                    if(confirm("Haluatko varmasti lähettää seuraavan kuvan: " +  $("#file").val().split('\\').pop())){
                        var imgName = 'default';
                        if($("#file").val()!= ''){
                            $("#file").upload("/photo",function(success){
                                //Ei toimi vielä, en saa ajax http vastausta ulos, onnistuiko kuvan lähetys
                            });
                            
                            
                            imgName = $("#file").val().split('\\').pop();
                            console.log("PAPSDPASD: " + imgName); 
                            client.send("/ws/messages", {}, JSON.stringify({'username': username, 'channel': channel, 'content': document.getElementById('message').value, 'time':time,'image':imgName}));
                            document.getElementById("file").disabled = true; 
                            document.getElementById("action-info").textContent = 'Lähetetään kuvaa..';
                        }
                    }
                };
                
                function init(){
                    client.send("/ws/getusers");
                }
                function linkify(message){
                    message = message.replace(/(www\..+?)(\s|$)/g, function(text, link) {
                       return '<a href="http://'+ link +'">'+ link +'</a>';
                        })
                    return message;    
                }
                
                function urlExists(url){
                        var http = new XMLHttpRequest();
                        http.open('HEAD', url, false);
                        http.send();
                        return http.status!=404;
                 }
                
                function buildMessage(message){
                    var paragraph = document.createElement("p");
                    paragraph.id = 'msgcontent';
  
                    
                    var msgParts = message.content.split(" ");
                    paragraph.appendChild(document.createElement('p'));
                    if(message.image!='default' && message.image!=null){
                        //window.alert(message.image);
                        var linkElement = document.createElement('a');
                        var img = document.createElement('img');
                        img.src = '/uploads/'+message.image;
                        img.height = 100;
                        img.length = 200;
                        img.id = 'msgimg';
                        linkElement.href = '/uploads/'+message.image;
                        linkElement.target = 'blank';
                        paragraph.appendChild(document.createElement('p'));
                        var countx = 0;
                        while(urlExists('/uploads/'+message.image)==false){
                            console.log("oottaa");
                            sleep(500);
                            if(countx>20){
                                break;
                            }
                            countx++;
                        }
                        console.log("löytyi");
                        
                        linkElement.appendChild(img);
                        
                        paragraph.appendChild(linkElement);
                        paragraph.appendChild(document.createElement('p'));
                        document.getElementById("file").value = "";
                        if(document.getElementById("file").disabled == true){
                            if(message.username==username){
                                document.getElementById("file").disabled = false;
                                document.getElementById("action-info").textContent = '';
                            }
                        }
                    }
                    
                    $.each(msgParts,function(i, word){
                        if(word.indexOf("www.") !== -1||word.indexOf("http:") !== -1||word.indexOf("https:") !== -1){
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
                    var paragraph2 = buildMessage(message);
                    var paragraph1 = document.createElement("p");
                    paragraph1.appendChild(document.createTextNode(message.time + ': ' + message.username + ': '));
                    
                    var oneMsgDiv = document.createElement('div');
                    oneMsgDiv.id = 'oneMsgDiv';
                    oneMsgDiv.appendChild(paragraph1);
                    oneMsgDiv.appendChild(paragraph2);
                    
                    document.getElementById("messages").appendChild(oneMsgDiv);
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
                
                
                function sleep(milliseconds) {
                    var start = new Date().getTime();
                    for (var i = 0; i < 1e7; i++) {
                      if ((new Date().getTime() - start) > milliseconds){
                        break;
                      }
                    }
                }
                
                

                


                
                
