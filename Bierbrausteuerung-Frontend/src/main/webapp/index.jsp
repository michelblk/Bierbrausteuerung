<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="de">
<head>
    <title>Brausteuerung</title>
    <link rel="stylesheet" href="include/css/bootstrap.css"/>
    <link rel="stylesheet" href="include/css/bootstrap-grid.css"/>
    <link rel="stylesheet" href="include/css/bootstrap-reboot.css"/>
    <link rel="stylesheet" href="include/css/main.css"/>
    <script src="include/js/jquery.js"></script>
    <script src="include/js/popper.js"></script>
    <script src="include/js/bootstrap.js"></script>
    <script src="include/js/main.js"></script>
</head>
<body>
Current Configuration:
<form id="configuration-container">
    <table>
        <thead>
        <tr>
            <td></td>
            <td>Temperatur</td>
            <td>Buffer</td>
            <td>Dauer (Minuten)</td>
            <td>Startzeit</td>
        </tr>
        </thead>
        <tbody id="configuration-table-body">
        <tr class="configuration-entry" id="configuration-template">
            <td>
                <div class="configuration-remove-button"></div>
            </td>
            <td>
                <input type="number" name="targetTemperature" step="0.1"
                       class="configuration-temperature form-control"/>
            </td>
            <td>
                <input type="number" name="buffer" step="0.1" class="configuration-buffer form-control"/>
            </td>
            <td>
                <input type="number" name="duration" class="configuration-duration form-control"/>
            </td>
            <td>
                <span class="configuration-start-time"></span>
            </td>
        </tr>
        </tbody>
        <tfoot>
        <tr>
            <td colspan="5" class="text-center">
                <input type="button" id="configuration-add-button" value="Hinzuf&uuml;gen"/>
            </td>
        </tr>
        </tfoot>
    </table>
    <input type="submit" class="form-control" value="Start"/>
</form>
</body>
</html>
