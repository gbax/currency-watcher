<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Currency Watcher</title>
</head>

<body>
<div id="content" role="main" class="currency-watcher__content">

    <div class="col-sm-6 col-sm-offset-3">
        <h2>Currency exchange rates for last month</h2>

        <div class="panel panel-default">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a data-toggle="tab" href="#panel1">Table</a>
                </li>
                <li>
                    <a data-toggle="tab" href="#panel2">Graph</a>
                </li>
            </ul>

            <div class="tab-content">
                <div id="panel1" class="tab-pane currency-watcher__tab-pane__table active">
                    <h3>Table</h3>
                    <table class="table">
                        <thead>
                        <tr>
                            <td>Date</td>
                            <td>USD/RUB</td>
                            <td>EUR/RUB</td>
                        </tr>
                        </thead>
                        <tbody class="js-currency-table"></tbody>
                    </table>

                </div>

                <div id="panel2" class="tab-pane currency-watcher__tab-pane__graph" >
                    <h3>Graph</h3>

                    <div class="currency-watcher__tab-pane__graph__container">
                        <svg width="800" height="550"></svg>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<asset:javascript src="d3.v3.js"/>
<asset:javascript src="jquery-2.2.0.min.js"/>
<asset:javascript src="moment.js"/>
</body>
</html>
