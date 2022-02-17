const ws=require('ws')

let server=new ws.Server({port:'8081'})

var mysql = require('mysql2');

var chet_db = mysql.createConnection({
    host     : '127.0.0.1',
    user     : 'gaser',
    password:"",
    port:3306,
    database : 'chets'
    
});

var users_db = mysql.createConnection({
    host     : '127.0.0.1',
    user     : 'gaser',
    password:"",
    port:3306,
    database : 'users'
    
});



chet_db.connect((err)=>{
    if(err){
        throw err
    }
})

users_db.connect((err)=>{
    if(err){
        throw err
    }
})


function getSQLdate(){
    let d=new Date()
    let god=d.getFullYear()
    let mesec=d.getMonth()
    let day=d.getDay()
    let h=d.getHours()
    let m=d.getMinutes()
    let sek=d.getSeconds()

    return god+"-"+mesec+"-"+day+" "+h+":"+m+":"+sek;
}



function radi_s_porukom(socket,massage){


    //razlog zasto je ovo necitko zato sto ovo database_connection.query(sql,(e)=>{}) je thread i nemogu da ga pretvorim u vise funkcija

    let user=massage.user;
    const sql="select * from users where ime='"+user.user+"' and passwords='"+user.password+"';"
    //console.log("sql 1 : "+sql)
    users_db.query(sql,(err,rezz,fil)=>{


        if(rezz.length!=0){

            //ako postoji account
            
            if(massage.content!=""){
                // salje se  neka poruka

                //potencijalni bug haker mozda posalje tudji id kao massage.account1
                const upd="insert into chets(id1,id2,massage,datum) values("+massage.account1+","+massage.account2+",'"+massage.content+"','"+getSQLdate()+"')"
                chet_db.query(upd,(err,nes,drugo)=>{
                  
                })

            }

            
            const sql1="select * from (select * from chets where (id1="+massage.account1+" and id2="+massage.account2+") or (id1="+massage.account2+" and id2="+massage.account1+") order by datum desc limit 10) as gas order by datum ;"
            //console.log('sql2 :'+sql1)
            chet_db.query(sql1,(err,rezult,fil)=>{
                //ako postoje poruke izmedju account1 i account2


                let massages=[];     
                for(let i=0;i<rezult.length;i++){
                
                    const sql3="select * from users where id="+rezult[i].id1+";"
                    users_db.query(sql3,(err,accounts,fil)=>{
                        
                        if(accounts!=undefined){
                            let send={
                                from:accounts[0].ime,
                                profile:accounts[0].profile_pic,
                                content:rezult[i].massage
                            }
                        massages.push(send)
                            
                        }
                        if(massages.length==rezult.length){
                            
                            socket.send(JSON.stringify(massages))
                        }
                    

                    }).on('end',(e)=>{

                    })


                }

                
                 
        
            })





        }else{

            // ako nepostoji account
            socket.close()
        }

    })

}


server.on('connection',(s)=>{


    let account1;
    let account2;

    s.on('message',(e)=>{
        
        

    let mes=JSON.parse(e);
    
    if(mes.hasOwnProperty('content') && mes.hasOwnProperty('user') && mes.hasOwnProperty('account1') && mes.hasOwnProperty('account2')) {

    radi_s_porukom(s,mes)    
    }


        
    })

    



})