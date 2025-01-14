<html>
<head>
<link href="PLUGINS_ROOT/org.robotframework.ide.eclipse.main.plugin.doc.user/help/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<a href="RED/../../../../help/index.html">RED - Robot Editor User Guide</a> &gt; <a href="RED/../../../../help/user_guide/user_guide.html">User guide</a> &gt; <a href="RED/../../../../help/user_guide/tools_integration.html">Integration with other tools</a> &gt; 
	<h2>How to integrate RED with Robotframework Maven Plugin</h2>
<ul>
<li>Download from <a class="external" href="http://sourceforge.net/projects/launch4j/files/launch4j-3/3.8/" target="_blank">launch4j</a>
			applicable for your OS package - we will use launch4j-3.8-win32.zip
			(it is standalone version, which requires only unzip)
		</li>
<li>Run <i>launch4j.exe</i> GUI application
		</li>
<li>In Basic tab set <i>Output file:</i> to be jython.exe
			application, the location and select <i>Dont't wrap the jar,
				launch only</i>.
			<p>
<img src="images/maven_3_basic.png"/>
</p>
</li>
<li>In Classpath tab switch on <i>Custom classpath </i> and set <i>Main
				class:</i> to org.python.util.jython. <br/>
			In <i>Edit item</i>: put robotframework*.jar and press Accept button.<br/>
			Repeat this to add %RF_JAR% variable.
			<p>
<img src="images/maven_4_classpath.jpg"/>
</p>
<p>
<img src="images/maven_4_classpath1.jpg"/>
</p>
</li>
<li>In Header tab just switch <i>Header type</i> from GUI to
			Console.
			<p>
<img src="images/maven_5_header.png"/>
</p>
</li>
<li>In JRE tab put in Min JRE version value 1.7.0.
			<p>
<img src="images/maven_6_jre.jpg"/>
</p>
</li>
<li>In main menu press <i>Build wrapper</i> (gear icon). The
			application will ask you for save configuration, it is required - the
			name of configuration and place where it will be located is optional.
		</li>
<li>If everything was done ok you will be able to see in Log text
			area information about Successfully created file. <br/>
<p>
<img src="images/maven_8_jre.png"/>
</p>
</li>
</ul>
<p>
		The created <i>jython.exe</i> file has to two possibilities to set <i>robotframework*.jar</i>
		location:
	</p>
<ol>
<li>via already set variable %RF_JAR% you can set it in <i>System
				Variables</i></li>
<li>using jar located in the same directory where jython.exe file
			is present</li>
</ol>
<p>The last test before integration with RED - is to test if
	executable file was compiled ok and will work.</p>
<p>Execute in command line:
	
	<div class="code">
		set RF_JAR=D:\userdata\RED\Desktop<br>
		set RF_JAR=C:\Users\RED\.m2\repository\org\robotframework\robotframework\3.0\robotframework-3.0.jar<br/>
		jython.exe -m robot.run --version
	</br></div>
<p>If output looks like: Robot Framework 3.0 (Jython 2.7.0 on java1.*) it means that you can 
	integrate jython.exe with RED.
	</p>
<p>To integrate jython.exe with RED:</p>
<ol>
<li>put <i>jython.exe</i> file to bin directory. It can be
			anywhere, but parent folder must be named as bin - i.e. <i>C:\bin\
		</i></li>
<li>It is easier to copy robotframework*.jar to this bin
			directory instead of set RF_JAR environment variable</li>
<li>run <i>eclipse.exe</i> or <i>RED.exe</i> depends on which RED
			version you are using
		</li>
<li>go to <code><a class="command" href="javascript:executeCommand('org.eclipse.ui.window.preferences(preferencePageId=org.robotframework.ide.eclipse.main.plugin.preferences.installed)')">
Window -> Preferences -> Robot Framework -> Installed frameworks</a></code>
</li>
<li>click <i>Add...</i> button and select bin directory from 1.
		</li>
<li>if everything is ok, information about Robot Framework
			version should be visible</li>
<li>when you will create RED project or execute Clean\Build, you
			should see in Robot Standard libraries standard libraries like i.e.
			BultIn with information about keywords, which they contains.</li>
</ol>
</p></body>
</html>