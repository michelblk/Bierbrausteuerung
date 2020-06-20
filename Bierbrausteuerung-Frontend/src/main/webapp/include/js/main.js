$(document).ready(function () {
    let onCurrentConfigurationLoaded = function (data) {
        data.forEach((value) => {
            let template = $("#configuration-template").clone();
            template.removeAttr("id");
            template.attr('data-id', value["id"]);
            template.find(".configuration-temperature").val(value["targetTemperature"]);
            template.find(".configuration-buffer").val(value["buffer"]);
            template.find(".configuration-duration").val(value["duration"]);
            template.find(".configuration-start-time").text(value["startedAt"]);
            $("#configuration-table-body").append(template);
        });
    };

    let onAddButtonClicked = function () {
        let template = $("#configuration-template").clone();
        template.removeAttr("id");
        $("#configuration-table-body").append(template);
    };

    let onRemoveButtonClicked = function () {
        $(this).parents("tr").remove();
    };

    let onConfigurationSubmit = function (e) {
        e.preventDefault();

        $.ajax({
            url: '/backend/rest/configuration',
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(buildConfigurationPayload()),
            dataType: "json",
            success: function () {
                alert("OK")
            }
        });

        return false;
    };

    function buildConfigurationPayload() {
        let payload = [];
        $('.configuration-entry:not(#configuration-template)').each(function (index, tr) {
            const id = $(tr).attr("data-id");
            const targetTemperature = $(tr).find(".configuration-temperature").val();
            const buffer = $(tr).find(".configuration-buffer").val();
            const duration = $(tr).find(".configuration-duration").val();

            if (targetTemperature != null && buffer != null && duration != null) {
                payload.push({
                    "id": null, // parseInt(id),
                    "targetTemperature": parseFloat(targetTemperature),
                    "buffer": parseFloat(buffer),
                    "duration": parseFloat(duration),
                    "startedAt": null
                });
            }
        });
        return payload;
    }

    function loadCurrentConfiguration(callback) {
        $.ajax({
            url: '/backend/rest/configuration',
            method: 'GET',
            success: callback,
            error: function () {
                alert("Error while loading current configuration");
            }
        });
    }


    loadCurrentConfiguration(onCurrentConfigurationLoaded);
    $("#configuration-add-button").click(onAddButtonClicked);
    $("#configuration-table-body").on("click", ".configuration-remove-button", onRemoveButtonClicked);
    $("#configuration-container").submit(onConfigurationSubmit);
});