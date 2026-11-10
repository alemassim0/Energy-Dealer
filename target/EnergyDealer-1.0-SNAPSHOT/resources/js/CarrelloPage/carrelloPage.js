function remove_item(product_id){
    prodotto_nome = document.getElementById("product_" + product_id + "_name").textContent;

    product_subtotal = document.getElementById("product_" + product_id + "_subtotal").textContent;
    product_subtotal = product_subtotal.split(":")[1].replace("€","");
    product_subtotal = parseFloat(product_subtotal.trim().replace(",",".")).toPrecision(3)

    console.log(product_subtotal);

    fetch('https://localhost:8443/carrello', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            "prodotto_nome": prodotto_nome,
        })
    })
        .then(response => alert(prodotto_nome + " è stato rimosso dal carrello"))
        .then(data => console.log(data));

    subtotal = document.getElementById("subtotal");
    subtotal_value = parseFloat(subtotal.textContent.replace("€","").trim().replace(",",".")).toPrecision(3);

    subtotal_value -= Number(product_subtotal);
    subtotal.textContent = parseFloat(subtotal_value).toPrecision(3) + "€";

    prodotto_container = document.getElementById("product_" + product_id);
    prodotto_container.style = "display: none";
}