$(document).ready(function () {
    $.get("/api/currency/month-rates")
        .done(function (data) {
            if (!Array.isArray(data)) {
                alert("Something goes wrong");
            }
            handleData(data);
            renderTable(data);
            renderGraph(data)
        })
        .fail(function () {
            alert("Something goes wrong");
        })
});

var handleData = function (data) {
    data.forEach(function (d) {
        d.mDate = moment.unix(d.date / 1000).format('DD.MM.YYYY');
    });
};

var renderTable = function (data) {
    data.sort(function (a, b) {
        return b.eur - a.eur
    });
    var maxEur = data[0];
    data.sort(function (a, b) {
        return b.usd - a.usd
    });
    var maxUsd = data[0];
    data.forEach(function (d) {
        d.isMaxEur = d.date === maxEur.date;
        d.isMaxUsd = d.date === maxUsd.date
    });
    data.sort(function (a, b) {
        return a.date - b.date
    });
    $('.js-currency-table').append(data.map(function (d) {
        return rowTemplate(d);
    }).join(''));

};

var rowTemplate = function (rowData) {
    return '<tr>' +
        '<td>' + rowData.mDate + '</td>' +
        '<td class="' + (rowData.isMaxUsd ? 'success' : '') + '">' + rowData.usd + '</td>' +
        '<td class="' + (rowData.isMaxEur ? 'success' : '') + '">' + rowData.eur + '</td>' +
        '</tr>'
};

var renderGraph = function (data) {

    data.sort(function (a, b) {
        return a.date - b.date
    });

    var margin = {top: 20, right: 80, bottom: 30, left: 50},
        width = 800 - margin.left - margin.right,
        height = 550 - margin.top - margin.bottom;


    var x = d3.time.scale()
        .range([0, width]);

    var y = d3.scale.linear()
        .range([height, 0]);

    var color = d3.scale.category10();

    var xAxis = d3.svg.axis()
        .scale(x)
        .orient("bottom").ticks(10)
        .tickFormat(d3.time.format("%d.%m"));

    var yAxis = d3.svg.axis()
        .scale(y)
        .orient("left");

    var line = d3.svg.line()
        .x(function (d) {
            return x(d.date);
        })
        .y(function (d) {
            return y(d.value);
        });

    var svg = d3.select("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

    color.domain(['USD', 'EUR']);

    data.forEach(function (d) {
        d.date = moment.unix(d.date / 1000).toDate();
        d.eur = +d.eur;
        d.usd = +d.usd;
    });

    var currencies = color.domain().map(function (name) {
        return {
            name: name,
            values: data.map(function (d) {
                return {date: d.date, value: +d[name.toLowerCase()]};
            })
        };
    });

    x.domain(d3.extent(data, function (d) {
        return d.date;
    }));


    y.domain([
        d3.min(currencies, function (c) {
            return d3.min(c.values, function (v) {
                return v.value;
            });
        }),
        d3.max(currencies, function (c) {
            return d3.max(c.values, function (v) {
                return v.value;
            });
        })
    ]);

    svg.append("g")
        .attr("class", "x axis")
        .attr("transform", "translate(0," + height + ")")
        .call(xAxis);

    svg.append("g")
        .attr("class", "y axis")
        .call(yAxis);

    var currency = svg.selectAll(".currency")
        .data(currencies)
        .enter().append("g")
        .attr("class", "currency");

    currency.append("path")
        .attr("class", "line")
        .attr("d", function (d) {
            return line(d.values);
        })
        .style("stroke", function (d) {
            return color(d.name);
        });

    currency.append("text")
        .datum(function (d) {
            return {name: d.name, value: d.values[d.values.length - 1]};
        })
        .attr("transform", function (d) {
            return "translate(" + x(d.value.date) + "," + y(d.value.value) + ")";
        })
        .attr("x", 3)
        .attr("dy", ".35em")
        .text(function (d) {
            return d.name;
        });

    currency.selectAll("circle")
        .data(function (d) {
            return d.values
        })
        .enter()
        .append("circle")
        .attr("r", 3)
        .attr("cx", function (d) {
            return x(d.date);
        })
        .attr("cy", function (d) {
            return y(d.value);
        })
        .style("fill", function (d, i, j) {
            return color(currencies[j].name);
        });

};
