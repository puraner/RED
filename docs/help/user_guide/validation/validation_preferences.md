<html>
<head>
<link href="PLUGINS_ROOT/org.robotframework.ide.eclipse.main.plugin.doc.user/help/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<a href="RED/../../../../help/index.html">RED - Robot Editor User Guide</a> &gt; <a href="RED/../../../../help/user_guide/user_guide.html">User guide</a> &gt; <a href="RED/../../../../help/user_guide/validation.html">Validation</a> &gt; 
<h2>Validation - setting issues severity levels</h2>
<h3>General information</h3>
<p>Validation findings levels can be customized in Preferences (<code><a class="command" href="javascript:executeCommand('org.eclipse.ui.window.preferences(preferencePageId=org.robotframework.ide.eclipse.main.plugin.preferences.validation)')">
Window -> Preferences -> Robot Framework -> Errors/Warnings</a></code>).<br/>
Each issue can be reported as error/warning/info/ignore. In case of setting ignore level, such validation issue type will not be visible in Problems view.
</p>
<dl class="note">
<dt>Note</dt>
<dd>There are RED specific validation types grouped under Project configuration category, which are essential for RED and can have Fatal severity level. 
Such problems are reported even when validation is turned off, and building as well as validation is cancelled then.
   </dd>
</dl>
<h3>Validation related Error/Warnings types with examples</h3>
<h4>Code style</h4>
<ul>
<li><b>Keyword from nested library</b> - occurs when keyword imported by dependency is used in test suite.</li>
<li><b>Keyword occurrence not consistent with definition</b> - occurs when name in keyword call is different than in definition. Use name the same as in definition.</li>
<li><b>Keyword name with dots</b> - occurs when keyword name contains dots. It may be confused with fully qualified name.</li>
<li><b>Variable given as keyword name</b> - occurs when variable is used as keyword call in test suite setup or teardown.</li>
<li><b>Collection size should be equal to keyword arguments number</b> - occurs when collection variable is used in keyword call and collection elements number is different than keyword arguments number.</li>
<li><b>Invalid time format</b> - occurs when time is not formatted correctly. Use number, time string or timer string.</li>
<li><b>Variable declared without assignment</b> - occurs when variable is declared without assignment in Variables section.</li>
</ul>
<h4>Name shadowing and conflicts</h4>
<ul>
<li><b>Duplicated variable name</b> - occurs when variable name is duplicated and one variable value overrides another.</li>
<li><b>Duplicated test case name</b> - occurs when test case name is duplicated and both test cases can be run</li>
<li><b>Masked keyword name</b> - occurs when keyword defined in test suite has the same name like keyword from imported library. You can use fully qualified name when calling masked keyword.</li>
</ul>
<h4>Unnecessary code</h4>
<ul>
<li><b>Empty settings definition</b> - occurs when suite, test case or keyword setting is defined with empty content.</li>
<li><b>Unrecognized header type</b> - occurs when Robot Framework does not recognize section header. Only ***Settings***, ***Variables***, ***Test Cases*** or ***Keywords*** sections are valid.</li>
<li><b>Duplicated configuration path</b> - occurs when path defined in configuration is subpath of different one. Such path is skipped.</li>
<li><b>Missing configuration path</b> - occurs when missing path is defined in configuration. Such path is skipped.</li>
</ul>
<h4>Import</h4>
<ul>
<li><b>Absolute path is used</b> - occurs when absolute path is used. Workspace-relative paths are preferred in RED.</li>
<li><b>Unsupported resource import used</b> - occurs when imported file is in HTML format or is outside of workspace. Red will not parse such files, so keywords and variables defined inside will not be accessible. Use supported formats from workspace only.</li>
<li><b>Import path relative via modules path</b> - occurs when imported path is relative to python path.</li>
<li><b>Import path outside of workspace</b> - occurs when imported path points to location not from workspace.</li>
<li><b>Import Remote library without arguments</b> - occurs when Remote library is imported without arguments.</li>
</ul>
<h4>Robot version</h4>
<ul>
<li><b>Removed Robot Framework API used</b> - occurs when syntax from older Robot Framework version is not available in current version.</li>
<li><b>Unsupported Robot Framework API used</b> - occurs when syntax from newer Robot Framework version is not available in older version.</li>
<li><b>Deprecated Robot Framework API used</b> - occurs when deprecated syntax is used. Use current Robot Framework syntax instead.</li>
<li><b>Duplicated definitions used</b> - occurs when testcase or keywords definitions names are not unique. Refers to syntax in pre Robot Framework 2.9.0.</li>
<li><b>Incorrect variable initialization</b> - occurs when there is syntax error in variable initialization. Refers to syntax in pre Robot Framework 2.9.0.</li>
</ul>
<h3>Runtime and Building related Error/Warnings types with examples</h3>
<h4>Runtime</h4>
<ul>
<li><b>RED parser warning</b> - occurs when for some reason RED parser reports warning.</li>
<li><b>Robot Framework runtime error</b> - occurs when incorrect Robot Framework syntax is issued. Such syntax will fail test in runtime.</li>
</ul>
<h4>Project configuration</h4>
<ul>
<li><b>Project configuration file (red.xml) cannot be read</b> - occurs when project has no red.xml configuration file or it cannot be read.</li>
<li><b>Python Robot Framework environment missing</b> - occurs when there is no Robot Environment defined. Python main directory with Robot modules installed should be defined in preferences. Project may override this setting in its configuration file.</li>
<li><b>Library documentation file cannot be generated</b> - occurs when for some reason Robot framework is unable to generate library specification file, probably due to missing library dependencies or errors in library source code.</li>
</ul>
</body>
</html>