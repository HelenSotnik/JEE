function clickTest() {
    let a = parseInt(document.getElementById("a").value);
    let b = parseInt(document.getElementById("b").value);
    document.getElementById("result").textContent = sum(a, b);
}

function sum(a, b) {
    return a + b;
}