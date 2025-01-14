<html>
<head>
<link href="PLUGINS_ROOT/org.robotframework.ide.eclipse.main.plugin.doc.user/help/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<a href="RED/../../../../../help/index.html">RED - Robot Editor User Guide</a> &gt; <a href="RED/../../../../../help/user_guide/user_guide.html">User guide</a> &gt; <a href="RED/../../../../../help/user_guide/launching.html">Launching Tests</a> &gt; <a href="RED/../../../../../help/user_guide/launching/debug.html">Debugging Robot</a> &gt; 
	<h2>Debugging Robot &amp; Python with RED &amp; PyDev</h2>
<p>It is possible to setup debugging sessions on both Robot and Python code levels using RED together with
	PyDev in single RED/Eclipse instance.
	</p>
<h3>Prerequisites</h3>
<ul>
<li>Any Eclipse-based installation with both RED and PyDev installed,
		</li>
<li>the <code>pydevd</code> debugger module - this is provided by PyDev or manually, can be also installed into 
		Python with <code>pip</code>,
		</li>
<li>the <code>redpydevd</code> runner script - this is provided by RED or manually.
		</li>
</ul>
<p>In order to setup debugging sessions proper launch configuration needs to be created. In order to do this 
	one can simply run a wizard: <code><a class="command" href="javascript:executeCommand('org.eclipse.ui.newWizard(newWizardId=org.robotframework.red.wizard.new.redPydevdLaunchConfig)')">
    New  -> Other -> Robot Framework -> RED with PyDev debugging session</a></code>. The launch configuration
    created by this wizard will be created for selected suites inside project (if there is a suite selected in Project
    Explorer view).
	</p>
<h3>Wizard</h3>
<h4>Choosing Python interpreter</h4>
<p>First page of wizard allows to choose any environment from those defined in Preferences. It will be used
	to execute the <code>redpydevd</code> runner script and effectively the chosen Robot suites. 
	</p>
<img src="images/red_pydev_wizard_envs.png"/>
<br/>
<br/>
<h4>Choosing redpydevd module</h4>
<img src="images/red_pydev_wizard_runner.png"/>
<p>Second page allows to choose which <code>redpydevd</code> script should be used. There are 3 possibilities:
	</p>
<ul>
<li>The one installed in chosen Python interpreter - if there is no <code>redpydevd</code> installed or
	   is outdated (in different version than currently supported) then RED will use <code>sdist</code> and <code>pip</code>
	   to install that module (offline),
       <p></p>
</li>
<li>the one exported to chosen location in your system - the script file will be written in this location
	   and used by created launch configuration,
       <p></p>
</li>
<li>the one already existing in chosen location.
	   </li>
</ul>
<br/>
<dl class="note">
<dt>Note</dt>
<dd>The version of <code>redpydevd</code> module expected by RED is displayed at this wizard page.
       Make sure to use the same version if you're using already existing script exported to local location some time ago.
       </dd>
</dl>
<h4>Choosing pydevd module</h4>
<img src="images/red_pydev_wizard_dbg.png"/>
<p>Last page allows to choose which <code>pydevd</code> module should be used. There are 3 possibilities:
    </p>
<ul>
<li>The one distributed with PyDev - this option is recommended because it ensures that the version of 
       module is the same as PyDev would expect; this option may be disabled if the module couldn't be located
       in your PyDev installation,
       <p></p>
</li>
<li>the one installed in chosen Python interpreter - this option is disabled if <code>pydevd</code> is not
       installed. You need to manually install it using <code>pip</code> to enable it. Not that the version may
       be different than the one provided by PyDev as visible on screen above,
       <p></p>
</li>
<li>the one already existing in chosen location - if you happen to download <code>pydevd</code> from elsewhere.
       </li>
</ul>
<p>It is also possible to set the address and port on which PyDev will communicate with the module.
    </p>
<dl class="warning">
<dt>Warning</dt>
<dd>If the libraries you are going to debug use <code>Gevent</code> library then please select <b>Gevent 
       compatible</b> checkbox. This will add <code>GEVENT_SUPPORT</code> environment variable set to <code>True</code>.
       It is required for <code>pydevd</code> in order to be able to debug such libraries. Without this the debugger
       sessions may hang.
       </dd>
</dl>
<h4>Created launch configuration</h4>
<p>After clicking <b>Finish</b> RED will install or export <code>redpydevd</code> runner (if neeeded) and create
    new launch configuration with prefilled <b>External script</b> settings at <b>Executor</b> tab. For example:
    </p>
<img src="images/red_pydev_wizard_cfg.png"/>
<h3>Debugging</h3>
<p>In order to be able to debug: the project and suite(s) needs to be defined at <b>Robot</b> tab of launch
    configuration. It may be already defined if suite was selected in Project Explorer view when starting wizard. 
    Next one need to: 
    </p>
<ul>
<li>Put a breakpoint either in selected suite code (.robot file) or in Python code. Note that PyDev requires
        <b>Debug</b> perspective to be first activated (see: <a class="external" href="http://www.pydev.org/manual_adv_remote_debugger.html" target="_blank">PyDev Remote Debugger User Guide</a>).
        <p></p>
</li>
<li>the <b>Python debug server</b> needs to be started. This can be done manually:<br/>
<img src="images/red_pydev_wizard_server.png"/><br/>
        or automatically if proper PyDev preference is enabled (<code>Preferences -> PyDev -> Debug -> Remote debugger server activation</code>).
        <p></p>
</li>
<li>the address and port passed by launch configuration for Python debugger need to be the same 
        as PyDev is using (only needed if changed to non-default),
        <p></p>
</li>
<li>launch created configuration in debug mode.
        </li>
</ul>
<p>After launching one should see Robot debugger stopping at breakpoint:
    </p>
<img src="images/red_pydev_wizard_brkp.png"/>
<p>In this example the keyword <b><code>keyword</code></b> comes from tiny local library. Once <b>Step Into (F5)</b>
    is executed the editor should open:
    </p>
<img src="images/red_pydev_wizard_editor.png"/>
<p>This dummy <i>editor</i> explains that RED debugger cannot go into library keywords code on it's own but 
    PyDev can be used cooperate and be able to debug Python-level code. One can use <b>Click here</b> and RED should 
    open Python script where keyword is defined:
    </p>
<img src="images/red_pydev_wizard_pybrkp.png"/>
<p>Put a breakpoint there and resume Robot code execution. Now PyDev debugger should pause allowing to debug 
    Python code (note there are two sessions in <b>Debug</b> view - one of PyDev operating on Python code and 
    second of RED operating on RF code):
    </p>
<img src="images/red_pydev_wizard_pause.png"/>
</body>
</html>