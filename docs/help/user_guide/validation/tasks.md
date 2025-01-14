<html>
<head>
<link href="PLUGINS_ROOT/org.robotframework.ide.eclipse.main.plugin.doc.user/help/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<a href="RED/../../../../help/index.html">RED - Robot Editor User Guide</a> &gt; <a href="RED/../../../../help/user_guide/user_guide.html">User guide</a> &gt; <a href="RED/../../../../help/user_guide/validation.html">Validation</a> &gt; 
<h2>Detecting tasks/TODO defined in comments</h2>
<p>It is a common practice to put notes in comments describing some kind of tasks which needs to be done
in future. RED offers possibility to detect such tasks and overview them in single place. 
</p>
<p>Tasks detection is done during validation phase, so the validation itself has to be enabled too. Once everything is
enabled RED will start reporting special kinds of markers similarly as Problems, but of different type (Task).
For example in following code there are two tasks defined in comments:
</p>
<div class="code">
# TODO implement more test scenarios<br/>
*** Test Cases ***<br/>
case<br/>
    &nbsp;&nbsp;&nbsp;&nbsp;Should Be Equal&nbsp;&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;2&nbsp;&nbsp;&nbsp;&nbsp;# FIXME this assertion does not pass<br/>
    &nbsp;&nbsp;&nbsp;&nbsp;...
</div>
<img src="images/editor_tasks.png"/><br/>
<p>The detection is based on tags. When defined tag is found by RED inside the comment it will be reported as a
<b>task</b>. By default there are two tags which RED recognizes: <b>TODO</b> and <b>FIXME</b>: the first
one has normal priority while the latter is reported with high priority. The tags and their priorities can 
be specified in <a href="#prefs">preferences</a>.
</p>
<img src="images/tasks.png"/><br/>
<p>Detected tasks are visible in RED in couple of places:</p>
<ul>
<li><b>Tasks</b> view - this view is similar to Problems view and displays all the tasks reported for files in whole
  workspace (this view can be open from menu by choosing <b><code>Window -> Show View -> Other... -> General -> Tasks</code></b>),
  </li>
<li><b>Source</b> tab of editor - icon is visible in line where task is defined on left vertical ruler as well
  as small square on right overview vertical ruler,
  </li>
<li><b>Tables</b> tab of editor - icon is visible on element to which the task is attached on left ruler,
  </li>
<li><b>syntax coloring</b> of comment - the detected tag is colored differently than the rest of the comment both 
  in source tab as well as tables
  </li>
</ul>
<h3 id="prefs">Preferences</h3>
<p>Following preferences can be changed regarding tasks detection:</p>
<ul>
<li><b>detection enablement</b> - whole mechanism can be enabled or disabled at
    <i><a class="command" href="javascript:executeCommand('org.eclipse.ui.window.preferences(preferencePageId=org.robotframework.ide.eclipse.main.plugin.preferences.tasks)')">
    Task Tags</a></i> page,
    </li>
<li><b>tags and their priorities</b> - different tags can be specified at same 
    <i><a class="command" href="javascript:executeCommand('org.eclipse.ui.window.preferences(preferencePageId=org.robotframework.ide.eclipse.main.plugin.preferences.tasks)')">
    Task Tags</a></i> page,
    </li>
<li><b>tags syntax coloring</b> - the color of syntax highlighting for task tags can be specified at
    <i><a class="command" href="javascript:executeCommand('org.eclipse.ui.window.preferences(preferencePageId=org.robotframework.ide.eclipse.main.plugin.preferences.editor.syntax)')">
    Syntax Coloring</a></i> page,
    </li>
<li><b>marker annotations</b> color and appearance used by editor can be changed at
    <i><a class="command" href="javascript:executeCommand('org.eclipse.ui.window.preferences(preferencePageId=org.eclipse.ui.editors.preferencePages.Annotations)')">
    Annotations</a></i> page. 
</li></ul>
</body>
</html>