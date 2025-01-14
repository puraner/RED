<html>
<head>
<link href="PLUGINS_ROOT/org.robotframework.ide.eclipse.main.plugin.doc.user/help/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<a href="RED/../../../../help/index.html">RED - Robot Editor User Guide</a> &gt; <a href="RED/../../../../help/user_guide/user_guide.html">User guide</a> &gt; <a href="RED/../../../../help/user_guide/working_with_RED.html">Working with RED</a> &gt; 

<h2>Working with Remote Library</h2>
<h3>Introduction</h3>
<p>Following <a class="external" href="https://github.com/robotframework/RemoteInterface" target="_blank">RobotFramework RemoteInterface</a></p>
<p>The remote library interface allows Robot Framework test libraries to be run as external processes. An important use case for this support is running test libraries on different machines than where Robot Framework itself is executed.Another big use case is implementing test libraries using other languages that Robot Framework supports natively. In practice test libraries can be implemented using any language that supports the XML-RPC protocol that the remote interface uses for communication. </p>
<p>The remote interface consists of the Remote library, remote servers and actual test libraries running behind these servers. </p>
<p>The Remote library is one of Robot Framework's standard libraries and thus automatically installed with the framework. It does not have any keywords of its own, but instead works as a proxy between Robot Framework and remote servers.<br/>

Remote servers expose the keywords provided by the actual test libraries to the Remote library. The Remote library and remote servers communicate using a simple remote protocol on top of an XML-RPC channel.</p>
<h3>Using Remote libraries in RED</h3>
<p>In order to use Keywords from Remote Library together with validation and code assistance, information about Remote Library address needs to be included in <b><code>red.xml -> Libraries -> Libraries</code></b> part.</p>
<br/><br/><img src="images/remote_library_settings.png"/> <br/><br/>
<p>Note that IP, Port and name library served by remote library server may differ. Please follow usage details of your Remote Library.</p>
<h4>Settings in TestSuite</h4>
<p>As with standard libraries, Remote library needs to be imported in <i>Settings</i> part.</p>
<p>Standard syntax is as follows: <i>Library Remote http://${ADDRESS}:${PORT}</i></p>
<br/><br/><img src="images/remote_library_testcase.png"/> <br/><br/>
<p>For more examples please check RobotFramewoke RemoteInterface webpage <a class="external" href="https://github.com/robotframework/RemoteInterface#available-remote-servers" target="_blank">https://github.com/robotframework/RemoteInterface#available-remote-servers</a></p>
</body>
</html>