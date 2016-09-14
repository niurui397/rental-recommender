$(function() {
  
  function validate(input) {
    var number = new RegExp("^[0-9]+$");
    if (input === undefined || input === "" || !number.test(input)) {
      return false;
    }
    return true;
  }

  $("#priceButton").on('click', function() {
    var bedrooms = $("#bedroomSelect option:selected").val(),
        bathrooms = $("#bathroomSelect option:selected").val(),
        squareFeet = $("#squareFeet").val(),
        url = "http://localhost:8080/recommendation?bedroomCount=" + bedrooms + "&bathroomCount=" + bathrooms + "&squareFeet=" + squareFeet;
    if (!validate(bedrooms)) {
      $("#inputError").text("Please select a bedroom number of your property").show();
      return;
    }

    if (!validate(bathrooms)) {
      $("#inputError").text("Please select a bathroom number of your property").show();
      return;
    }

    if (!validate(squareFeet)) {
      $("#inputError").text("Please type in the square feet value of your property").show();
      return;
    }

    $.ajax({
      url: url,
      dataType: 'json',
      type: 'GET',
      success: function(result) {
        $("#inputError").hide();
        $("#bedrooms").text(result.bedroomCount);
        $("#bathrooms").text(result.bathroomCount);
        $("#minSqrtFt").text("$" + result.minPricePerSqrFeet.toFixed(2));
        $("#maxSqrtFt").text("$" + result.maxPricePerSqrFeet.toFixed(2));
        $("#recommendation").text("$" + result.recommendPrice);
        $(".result-div").show();
      },
      error: function(xhr, status, err) {
        console.error(url, status, err.toString());
      }
    });
  })
});