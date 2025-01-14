<html>
<head>
<link href="PLUGINS_ROOT/org.robotframework.ide.eclipse.main.plugin.doc.user/help/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<a href="RED/../../../../help/index.html">RED - Robot Editor User Guide</a> &gt; <a href="RED/../../../../help/user_guide/user_guide.html">User guide</a> &gt; <a href="RED/../../../../help/user_guide/launching.html">Launching Tests</a> &gt; 
	<h2>Locally launched tests using own scripts</h2>
<p>The <b>Robot</b> launch configurations may be called through some user-defined executable/script. 
	This may be useful when integrating tests launches from RED with other tools like PyDev for 
	Robot &amp; Python debugging, Maven, Gradle, etc.
	</p>
<p>The general idea is that the command line call which RED executes for launching is wrapped
	with a call to user defined executable. For example RED would normally use following command
	line call:
	</p>
<div class="code"><code>
	python.exe -m robot.run --suite mySuite &lt;path to project&gt;
	</code></div>
<p>but when script <code>my_script.bat</code> with arguments <code>arg1</code>, <code>arg2</code> is
	used the command line call becomes:
	</p>
<div class="code"><code>
	my_script.bat arg1 arg2 python.exe -m robot.run --suite mySuite &lt;path to project&gt;
	</code></div>
<p>The script is now free to process the arguments which were passed - it may use them or not, or
	select those which are interesting for the script but eventually it should start robot tests execution.
	</p>
<dl class="note">
<dt>Note</dt>
<dd>By default, RED passes Robot executable command line to user script as is thus each space separated entry is own parameter.
       From above example, following Robot command line passed to script by RED:
       	<div class="code"><code>
            python.exe -m robot.run --suite mySuite &lt;path to project&gt;
        </code></div>
        is passed to a user script as 6 arguments. This can be changed in <a href="launch_prefs.html">preferences</a>, so whole Robot executable command line is wrapped with quotation marks. This affects how script handles input parameters.
	   </dd>
</dl>
<h3>Defining script call in launch configuration</h3>
<p>Script/executable to be used when launching is defined in launch configuration dialog in <b>Executor</b> tab under <b>External script</b> part:</p>
<img src="images/local_config_exec.png"/>
<p><b>Executable file</b> field is path to executable file from local system. Under Windows this may be <code>.exe</code>,
	<code>.bat</code> or <code>.com</code> file. Under Linux this may be any binary executable, but also 
	any text script file which contains 
	<a class="external" href="https://en.wikipedia.org/wiki/Shebang_(Unix)" target="_blank">shebang</a> line - just 
	remember that this file need to have <code>x</code> permission granted, so that the system will allow to
	execute it.
	</p>
<p><b>Additional executable file arguments</b> holds any arguments which are required by the script.</p>
<dl class="note">
<dt>Note</dt>
<dd>Default values for both executable and arguments fields can be defined in 
	   <a href="launch_prefs.html">preferences</a>, so every
	   time when RED is creating new launch configuration it will use those values. It may be useful if
	   you want to always use some script without manually changing launch configurations before launching.
       
	   </dd>
</dl>
<h3>Simple example</h3>
<dl class="note">
<dt>Note</dt>
<dd>User scripts examples can be found at <a class="external" href="https://github.com/nokia/RED/tree/master/src/RobotUserScripts" target="_blank">https://github.com/nokia/RED/tree/master/src/RobotUserScripts</a>.
	   </dd>
</dl>
<p>Windows batch example:</p>
<div <code="" class="code">
    @ECHO OFF</div></body></html>

    echo running scripts with external batch file
    echo script name: %0
    echo script's arguments: %*
    echo running arguments as they consist call to start python scripts:
    %*

Python script example:

<div class="code"><code>
	import sys<br/>
	from io import StringIO<br/>
	from subprocess import Popen, PIPE<br/><br/>
	
	print('##########')<br/>
	print('Running Robot tests via script!')<br/>
	print('##########')<br/>
	sys.stdout.flush()<br/><br/>
	
	execution = Popen(sys.argv[1:])<br/>
	execution.communicate()<br/>
</code></div>

Save code from above into `` my_script.py `` file, then at __Executor__ tab	of desired launch configuration browse your computer for __python.exe__ and set it in	__executable file__ field and pass the location to `` my_script.py `` inside	__arguments field__.	

When configuration defined as described will be launched you should be able to see the message	from script as well as the output from tests.	

  

  