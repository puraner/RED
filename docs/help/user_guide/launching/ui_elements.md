<html>
<head>
<link href="PLUGINS_ROOT/org.robotframework.ide.eclipse.main.plugin.doc.user/help/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<a href="RED/../../../../help/index.html">RED - Robot Editor User Guide</a> &gt; <a href="RED/../../../../help/user_guide/user_guide.html">User guide</a> &gt; <a href="RED/../../../../help/user_guide/launching.html">Launching Tests</a> &gt; 
	<h2>Launching related UI elements</h2>
<p>RED provides couple of elements which are used in order to launch test and communicate with
	them when they are running. 
	</p>
<h3 id="launching">Launching tests</h3>
<p>Launch configuration has to be created to launch Robot tests. This can be done either manually
	or automatically e.g. when launching tests from <b>Project Explorer</b> or from <b>Suite Editor</b>.
	</p>
<ul>
<li><b>Launch Configuration dialog</b> - open launch configurations dialog (via 
		<code><a class="command" href="javascript:executeCommand('org.eclipse.debug.ui.commands.OpenRunConfigurations')">
		Run -> Run Configurations...</a></code> or
		<code><a class="command" href="javascript:executeCommand('org.eclipse.debug.ui.commands.OpenDebugConfigurations')">
		Run -> Debug Configurations...</a></code> menu) and create new configuration under <i>Robot</i> or
		<i>Robot Remote</i> element. For detailed description of both configurations and their possible 
		attributes read <a href="local_launch.html">Local launches</a> or 
		<a href="remote_launch.html">Remote launches</a> topic. After creating the configuration it can be launched
		manually by clicking <b>Run</b>/<b>Debug</b> button.
		<p><img src="images/ui_run_configs.png"/></p>
</li>
<li><b>From launching history</b> - launch already existing and previously launched configuration. This 
		is possible either from top level <b>Run</b> menu or from launching toolbar as depicted on images below:
		<p>
<img src="images/ui_launch_history_2.png"/>
<img src="images/ui_launch_history_1.png"/>
</p>
</li>
<li><b>Whole suites from Project Explorer</b> - launch tests for given suite(s). It can be either
		whole folder containing other suites or simple a suite file. RED will try to find already existing
		configuration basing on selected items and launch it or it will create new default one for given
		this selection and then launch it.
		<p>
<img src="images/ui_launch_explorer.png"/>
</p>
</li>
<li><b>Selected tests from Project Explorer</b> - similarly as above configuration for only selected
		tests inside some suite can be launched by clicking on tests inside particular suite file.
		<p>
<img src="images/ui_launch_explorer_tests.png"/>
</p>
</li>
<li><b>Selected tests from Suite Editor</b> - selected tests can also be launched straight from Suite
		Editor either in Test Cases page or in Source page. Use context menu or designated keyboard shortcut
		to perform launch.
		<p>
<img src="images/ui_launch_source.png"/>
<img src="images/ui_launch_table.png"/>
</p>
</li>
</ul>
<h3>Console view</h3>
<p>Console view is standard view provided by eclipse platform. RED is using in to display output of 
	the tests launch. In case of locally launched tests it displays standard output and standard error 
	(<i>stdout</i> and <i>stderr</i>) of process which runs the test. It also
	contains a header in which command line call and Robot version is shown. Remotely
	launched tests do not send those streams to RED, so in this case only messages about remote server
	status are displayed. 
	</p>
<img src="images/ui_console_view.png"/>
<dl class="note">
<dt>Note</dt>
<dd>You may notice that some links are clickable in output of tests - for example locations of 
		Output/Log/Report files as well as location of argument file (if in use). You can use them to 
		open the files right inside RED.</dd>
</dl>
<h3>Execution view</h3>
<p>Execution view is a view provided by RED in which RED is presenting suites tree together with
	status of tests/suites (PASS or FAIL). The view is updated during tests launch thus presenting
	current execution progress. Remotely launched tests may also use this view - the only requirement
	is that the tests itself has to take <a href="red_agent.html">agent</a> into execution as a listener.
	</p>
<img src="images/ui_execution_view.png"/>
<p>There are couple of actions possible within this view:
	</p>
<ul>
<li><b>Expand All</b> - expands whole tree,</li>
<li><b>Collapse All</b> - collapses the tree,</li>
<li><b>Show failures only</b> - filters the tree, so that only failed elements are presented,</li>
<li><b>Rerun Tests</b> - launches the configuration again,</li>
<li><b>Rerun Failed Tests Only</b> - launches the configuration with tests which failed.</li>
<li><b>Go To File</b> - from context menu on test (also by double click) - opens editor and selects
		test case.</li>
</ul>
<h3>Message Log view</h3>
<p>Message Log view is a view provided by RED which presents messages logged during tests execution. Like
	execution view it it is also updated during tests launch. Similarly it may also work with Remote 
	configurations when <a href="red_agent.html">agent</a> is taken into execution as a listener.
	</p>
<img src="images/ui_msglog_view.png"/>
<h3>Debug perspective</h3>
<p>RED uses standard eclipse <b>Debug</b> perspective when configuration is launched in debug mode. 
	For more information about working with this perspective please take a look at 
	<a href="debug.html">Debugging Robot</a> topic.
	</p>
</body>
</html>