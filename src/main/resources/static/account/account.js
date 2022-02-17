let zatvori=document.getElementById("X")
let ceoprozor=document.getElementById("minichet")
zatvori.onclick=(e)=>{
    ceoprozor.style.visibility='hidden'
}


let logoff=document.getElementById("log_off")

logoff.onclick=(e)=>{
    document.cookie=""

}
