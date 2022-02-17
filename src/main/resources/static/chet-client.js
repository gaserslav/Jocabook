
function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
  }


  


let refeshmentrate=1000
let title="gaser";
let ip="localhost"
let id_of_this_account;
let account2;
let makechet_button=document.getElementById("massages")



function pick_chet(account){

    account2=account
    openchet()

}

function openchet(){
    document.getElementById("minichet").style.visibility='visible'
}




function main(){

    let user={
        user:getCookie("user"),
        password:getCookie("password")
    }

    
let s=new WebSocket("ws://"+ip+":8081")

document.getElementById("minichet").style.visibility='hidden'

let account1=document.getElementById("pozitive").innerHTML




makechet_button.onclick=(e)=>{
    pick_chet(document.getElementById("target").innerHTML);
    
}


document.getElementById('sendmassage').onclick=(e)=>{
     
    s.send(JSON.stringify({content:document.getElementById('massage').value,account1:account1,account2:account2,user:user}))


}
s.onmessage=(e)=>{ 
    const massages=JSON.parse(e.data)
    let s=""
    for(let i=0;i<massages.length;i++){
    s+="<div style='height:12%'><img style='height:100%' src='"+massages[i].profile+"'> <span style='color:blue'>"+massages[i].from+"</span>:"+massages[i].content+"</div>"
    }
    document.getElementById('history_mini_chet').innerHTML=s;
    
}


s.onopen=()=>{



function refresh_me(){

    const prazno={content:"",account1:account1,account2:account2,user:user};

    s.send(JSON.stringify(prazno))
    setTimeout(refresh_me,refeshmentrate)
}

refresh_me()

}


}

main()