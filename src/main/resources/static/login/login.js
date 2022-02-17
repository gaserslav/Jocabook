
let x=300;
let y=0;
anime({
    targets:"#lopta",
    keyframes:[
        {translateX:x+500},
        {translateY:y+500},
        {translateX:x+-500},
        {translateY:y+-0}

    ],
    duration:3000,
    loop:2

})

setTimeout(()=>{
    document.getElementsByClassName("loading")[0].style.visibility='hidden'
    document.getElementsByClassName("glavno")[0].style.visibility='visible'

},6000)

document.getElementById('login').onclick=(e)=>{
    let user=document.getElementById("user").value;
    let password=hex_sha256(document.getElementById("password").value);
    let d=new Date(2030,0,1);
    document.cookie="user="+user+"; expires="+d.toUTCString();
    document.cookie="password="+password+"; expires="+d.toUTCString();
   
}

