<html>
<head>
<link href="PLUGINS_ROOT/org.robotframework.ide.eclipse.main.plugin.doc.user/help/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<a href="RED/../../../../help/index.html">RED - Robot Editor User Guide</a> &gt; <a href="RED/../../../../help/user_guide/user_guide.html">User guide</a> &gt; <a href="RED/../../../../help/user_guide/tools_integration.html">Integration with other tools</a> &gt; 
	<h2>Running tests using Gradle</h2>
<p>It is possible to run Robot tests from RED by launching them through script. 
	There are at least two different possibilities for running the tests from inside of
	<a class="external" href="https://gradle.org/" target="_blank">Gradle</a> building script:
	</p>
<ol>
<li>by explicitly running Python interpreter passing required arguments for robot</li>
<li>by using some Gradle plugin which will use Standalone Robot JAR distribution
		obtained from repository (see
		<a class="external" href="http://robotframework.org/robotframework/latest/RobotFrameworkUserGuide.html#standalone-jar-distribution" target="_blank">RF help topic about standalone JAR</a>)
		</li>
</ol>
<p>The first possibility requires having some python interpreter with RF installed 
	(CPython, PyPy, Jython, IronPython) while the latter only requires Java (which anyway has
	to be installed in order to run Gradle).
	</p>
<h3>Simple Gradle script running external interpreter</h3>
<p>We will use following simple script in order to run interpreter from RED: 
	</p>
<div class="code">
<code>
		task runRobot(type:Exec) {<br/>
   		&nbsp;&nbsp;&nbsp;&nbsp;executable robotExec<br/>
 		&nbsp;&nbsp;&nbsp;&nbsp;args Eval.me(robotArguments)<br/>
		}
		</code>
</div>
<p>the task above expects two arguments: path to interpreter executable and a list 
	of it's arguments written in a form: <code>['arg1', 'arg2', ..., 'arg_n']</code>
</p>
<p>When launching the test RED does not pass arguments in form like above, moreover 
	Gradle passes arguments mostly using it's own syntax: <code>-Parg=val</code>.
	Because of this we need to create own script which will perform arguments translation
	(or modify Gradle Wrapper script).
	</p>
<p>
    Script available on GitHub: <a class="external" href="https://github.com/nokia/RED/tree/master/src/RobotUserScripts" target="_blank">
		https://github.com/nokia/RED/tree/master/src/RobotUserScripts</a>
</p>
<ul>
<li><b>Windows batch script</b> - <code>gradlew_robot.bat</code>
<div class="code">
			@echo off<br/>
			set FIRST="true"<br/>
			set EXEC=%1<br/>
			set RF_ARGS=[<br/>
			shift<br/>
<br/>
			:loop1<br/>
			if "%1"=="" goto after_loop<br/>
			if %FIRST%=="true" (<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;set RF_ARGS=%RF_ARGS%'%1'<br/>
			) else (<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;set RF_ARGS=%RF_ARGS%, '%1'<br/>
			)<br/>
			shift<br/>
			set FIRST="false"<br/>
			goto loop1<br/>
<br/>
			:after_loop<br/>
			set RF_ARGS=%RF_ARGS:\=/%]<br/>
<br/>
			call gradlew.bat runRobot -ProbotExec=%EXEC% -ProbotArguments="%RF_ARGS%"<br/>
</div>
</li>
<li><b>Linux bash script</b> - <code>gradlew_robot.sh</code>
<div class="code">
			#!/usr/bin/env bash<br/>
<br/>
			first=1<br/>
			exec=$1<br/>
			restvar="["<br/>
<br/>
			shift<br/>
			for var in "$@"<br/>
			do<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;if [ $first -eq 1]; then<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;restvar="$restvar'$var'"<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;else<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;restvar="$restvar,'$var'"<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;fi<br/>
			&nbsp;&nbsp;&nbsp;&nbsp;first=0<br/>
			done<br/>
			restvar="$restvar]"<br/>
<br/>
			./gradlew runRobot -ProbotExec=$exec -ProbotArguments=$restvar<br/>
</div>
</li>
</ul>
<p>
		It is now possible to run tests with the script above: create Robot
		launch configuration and set executable file at <b>Executor</b> tab
		and launch the tests as depicted on images below.
	</p>
<p><img src="images/gradle_win.png"/></p>
<p><img src="images/gradle_linux.png"/></p>
<dl class="note">
<dt>Note</dt>
<dd>Instead of specifying script at <b>Executor</b> tab manually it is possible to define
	   default value of executor script path in Preferences
	   (at <code><a class="command" href="javascript:executeCommand('org.eclipse.ui.window.preferences(preferencePageId=org.robotframework.ide.eclipse.main.plugin.preferences.launch.default)')">
	   Window -> Preferences -> Robot Framework -> Default Launch Configurations
	   </a></code> set <b>Executable file</b> to desired executable). Now every time new launch 
	   configuration is created it will use given executable by default.
	   </dd>
</dl>
<h3>Gradle scripts running Standalone JAR distribution</h3>
<p>Running the tests through Gradle plugin which uses standalone JAR is very similar to running 
	the tests using external interpreter, the only thing is that 
	the script has to translate arguments into a form which is used by the plugin, which may of course vary
	depending on Gradle plugin in use. The batch/bash scripts from above may be adapted and used when calling
	tests this way.
	</p>
<dl class="warning">
<dt>Warning</dt>
<dd>As for now RED cannot use Standalone JAR distribution of RF as environment, so while it would 
	   possible to run the tests using (2) method it is currently not possible to 
	   have other features of RED like validation, code assistance etc. working without having 
	   python interpreter installed.
	   </dd>
</dl>
</body>
</html>