function clickTest() {
    let number1 = parseBigDecimal(document.getElementById("number1").value);
    let number2 = parseBigDecimal(document.getElementById("number2").value);
    document.getElementById("SUM").textContent = sum(number1, number2);
}

function sum(number1, number2) {
    return number1 + number2;
}