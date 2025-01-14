<html>
<head>
<link href="PLUGINS_ROOT/org.robotframework.ide.eclipse.main.plugin.doc.user/help/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<a href="RED/../../../../../help/index.html">RED - Robot Editor User Guide</a> &gt; <a href="RED/../../../../../help/user_guide/user_guide.html">User guide</a> &gt; <a href="RED/../../../../../help/user_guide/launching.html">Launching Tests</a> &gt; <a href="RED/../../../../../help/user_guide/launching/debug.html">Debugging Robot</a> &gt; 
	<h2>Breakpoints</h2>
<p>List of all breakpoints is displayed in <b>Breakpoints</b> view of Debug perspective.
	This view can be used to view and edit breakpoints even without any debugging session currently launched.
	</p>
<img src="images/debug_breakpoints.png"/>
<p>Using this view:
	</p>
<ul>
<li>each breakpoint can be enabled/disabled,
	    </li>
<li>each breakpoint can be removed,
	    </li>
<li>for each Robot breakpoint the attribute can be set:
			<ul>
<li><b>Hit count</b>: breakpoint will cause execution suspension only when instruction in the
				breakpoint line will be executed for the <code>N</code>-th time in session. 
				Useful to activate breakpoint inside a loop statement on certain iteration
				</li>
<li><b>Conditional</b>: breakpoint will cause execution suspension only when condition is satisfied.
	    		The condition should be a call to RF keyword written in RF syntax and it is considered satisfied if the
	    		ended with <b>PASS</b> status unsatisfied otherwise; for instance (remember about separators): 
	    		<div class="code">Should be Equal&nbsp;&nbsp;&nbsp;&nbsp;${variable}&nbsp;&nbsp;&nbsp;&nbsp;10
	    		</div> 
	    		is satisfied only if value of <code>${variable}</code> is equal to <code>10</code> at the moment
	    		when execution hits the line in which such breakpoint is placed.
	    		</li>
</ul><p></p>
</li>
<li>double click on breakpoint to open the file in which selected breakpoint is placed
	    </li>
</ul>
<br/>
<dl class="note">
<dt>Note</dt>
<dd>When breakpoint is edited the changes has to be saved. Unsaved changes in breakpoint are marked with 
	   <b>*</b> mark placed in Breakpoints view title similarly as in editors. Breakpoints are stored in workspace 
	   metadata so they are not removed when RED/eclipse is restarted.
	   </dd>
</dl>
</body>
</html>