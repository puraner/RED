<html>
<head>
<link href="PLUGINS_ROOT/org.robotframework.ide.eclipse.main.plugin.doc.user/help/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<a href="RED/../../../../help/index.html">RED - Robot Editor User Guide</a> &gt; <a href="RED/../../../../help/user_guide/user_guide.html">User guide</a> &gt; <a href="RED/../../../../help/user_guide/tools_integration.html">Integration with other tools</a> &gt; 
	<h2>Robot Framework Lint analysis</h2>
<p>Starting with RED 0.8.1 it is possible to run <a class="external" href="http://github.com/boakley/robotframework-lint/" target="_blank">Robot Framework Lint</a> 
	analysis tool. Of course one have to have it installed in the python installation
	used by the project.
	</p>
<h3>Running RfLint</h3>
<p>RfLint can be run on selected file or folder (or whole project) in <b>Project Explorer</b> view. In order
	to start analysis open context menu for selected resource and choose <b><code>Robot Framework -> Run RfLint analysis</code></b>.
	</p>
<img src="images/rflint_run.png"/>
<p>The analysis should start and its progress is visible in <b>Progress</b> view. At any time you can abort running
	validation:
	</p>
<img src="images/rflint_progress.png"/>
<p>Analysis performed from RED is reporting all the findings as <b>Problem markers</b> of a separate type called
	<b>RfLint Problem</b>. This is a different type than those reported by standard RED <a href="../validation.html">
	validation</a> mechanism (they have <b>Robot Problem</b> type). Overall this means that the findings are visible
	in <b>Problems</b> view and are also visible in editors. 
	</p>
<img src="images/rflint_problems.png"/>
<p>In order to remove problems simply choose <b><code>Robot Framework -> Clean RfLint problems</code></b> from context menu
	of selected resource.
	</p>
<dl class="note">
<dt>Note</dt>
<dd>Robot Framework Lint analysis is not run on excluded project parts (see more under topic <a href="../validation/scope.html">Limiting validation scope</a>.
	   </dd>
</dl>
<h3>Configuration</h3>
<p>It is possible to configure RfLint behavior in Preferences (
		<code><a class="command" href="javascript:executeCommand('org.eclipse.ui.window.preferences(preferencePageId=org.robotframework.ide.eclipse.main.plugin.preferences.rflint)')">
		Window -> Preferences -> Robot Framework -> Errors/Warnings -> RfLint validation</a></code>).
	</p>
<img src="images/rflint_preferences.png"/>
<p>The table shows all available rules grouped by the source files where they are defined. It is possible
	to:
	</p>
<ul>
<li>add <b>additional rules files</b> - those files will be attached with all the rules when running RfLint 
	    analysis,
	    <p></p>
<li><b>ignore</b> rules - by checking/unchecking them. By default all the rules are not ignored. It is also
	    possible to check/uncheck file which will apply to all the rules from this file,
	    <p></p>
</li>
<li>define rule <b>severity</b> - each rule can have severity specified: <b>Error</b> and <b>Warning</b>.
        Similarly one can change severity of a file which would change severities of all its rules, 
        <p></p>
</li>
<li>rule <b>configuration</b> - as described in <a class="external" href="http://github.com/boakley/robotframework-lint/wiki/How-to-write-custom-rules" target="_blank">RfLint Wiki</a>
        some rules can be parameterized: simply write arguments for given rule and remember that multiple arguments
        should be separated with colon (:) character.
        <p></p>
</li>
</li></ul>
<p>The preference page displays the rule documentation in order to have a quick rule overview. Custom command
	line arguments for RfLint can be passed using <b>Additional arguments for RfLint</b> field - refer toe RfLint 
	user guide for more details on possible arguments.
	</p>
<br/>
<h3>Displaying rules documentation</h3>
<p>It is possible to see the rule documentation straight from <b>Problems</b> view by using context menu
    action on given RfLint problem. The documentation will be displayed in <b>Documentation</b> view.
    </p>
<img src="images/rflint_showdoc.png"/>
<br/>
<br/>
</body>
</html>