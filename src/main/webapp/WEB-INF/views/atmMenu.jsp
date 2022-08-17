<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page import="com.atm.model.Account" %>
<!DOCTYPE html>  
<%

Account acc = (Account)request.getAttribute("aAccount");
int accountNo = acc.getAccountNumber();
int pin = acc.getPin();
%>
<html lang = "en">  
<head>  
<title> ATM </title>  
  
<style>  
  
#clear{  
width: 170px;  
border: 3px solid gray;  
    border-radius: 3px;  
    padding: 20px;  
    background-color: yellow;  
}  

#cancel{  
width: 170px;  
border: 3px solid gray;  
    border-radius: 3px;  
    padding: 20px;  
    background-color: red;  
} 

#confirm{  
width: 170px;  
border: 3px solid gray;  
    border-radius: 3px;  
    padding: 20px;  
    background-color: green;  
} 
  
#display{  
width: 245px;
height: 195px;   
border: 5px solid black;  
    border-radius: 3px;  
    padding: 20px;  
    margin: auto; 
    background-color: cyan; 
}

#option
{
 width: 40px;
 height: 50px;
 background:grey;
 position: relative;
-moz-border-radius:0 10px 10px 0;
-webkit-border-radius:0 10px 10px 0;
 border-radius:0 10px 10px 0;
}
#option:before
{ 
content:"";
position:absolute;
right: 100%;
width: 0;
height: 0;
border-top: 25px solid transparent;
border-right: 13px solid grey;
border-bottom: 25px solid transparent;
}

.formstyle  
{  
width: 625px;  
height: 530px;  
margin: auto;  
border: 3px solid skyblue;  
border-radius: 5px;  
padding: 20px;  
}  
  
.number-button  
{  
width: 24px;  
height: 30px;
background-color: grey;  
color: white;  
border: 1px solid gray;  
    border-radius: 5px;  
    padding: 26px;  
    margin: 5px;  
    font-size: 15px;  
}  

.container {  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr;
  grid-template-rows: 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr;
  grid-auto-columns: 1fr 1fr 1fr 1fr 1fr;
  grid-auto-rows: 1fr 1fr;
  gap: 0px 0px;
  grid-auto-flow: row;
}

.btn0 { grid-area: 8 / 2 / 9 / 3; }
.btn1 { grid-area: 5 / 1 / 6 / 2; }
.btn2 { grid-area: 5 / 2 / 6 / 3; }
.btn3 { grid-area: 5 / 3 / 6 / 4; }
.btn4 { grid-area: 6 / 1 / 7 / 2; }
.btn5 { grid-area: 6 / 2 / 7 / 3; }
.btn6 { grid-area: 6 / 3 / 7 / 4; }
.btn7 { grid-area: 7 / 1 / 8 / 2; }
.btn8 { grid-area: 7 / 2 / 8 / 3; }
.btn9 { grid-area: 7 / 3 / 8 / 4; }
.cancel { grid-area: 5 / 4 / 6 / 6; }
.clear { grid-area: 6 / 4 / 7 / 6; }
.confirm { grid-area: 8 / 4 / 9 / 6; }
.screen { grid-area: 1 / 1 / 5 / 5; }
.option1 { grid-area: 1 / 5 / 2 / 6; }
.option2 { grid-area: 4 / 5 / 5 / 6; }
.card1 { grid-area: 5 / 7 / 7 / 9; }
.card2 { grid-area: 7 / 7 / 9 / 9; }
.screenopt1 { grid-area: 1 / 3 / 2 / 5; text-align: right; vertical-align:middle; padding: 20px;}
.screenopt2 { grid-area: 4 / 3 / 5 / 5; text-align: right; vertical-align:middle; padding: 20px;}
.screentext {  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  grid-template-rows: 1fr 1fr 1fr;
  gap: 0px 0px;
  grid-auto-flow: row;
  grid-template-areas:
    ". . ."
    "screentextoutput screentextoutput screentextoutput"
    ". pinarea .";
  grid-area: 2 / 1 / 4 / 5;
}
.screentextoutput { grid-area: screentextoutput; text-align: center;}
.pinarea { grid-area: pinarea; text-align: center;}

}

</style>  
<script>

	function checkbalance(){
		document.frmAtm.action = "BALANCECHECK"
		document.frmAtm.submit();
	}
	
	function withdraw(){
		document.frmAtm.action = "WITHDRAWAL"
		document.frmAtm.submit();
	}
	
	function cancelBtn(){
		document.frmAtm.action = "LOGIN";
		document.frmAtm.aAccountNumber.value = "";
		document.frmAtm.aPin.value = "";
		document.frmAtm.submit();
	}
</script>
  
</head>  
<body>   
<div class= "formstyle" >  

<form:form name="frmAtm" action="" method="get" modelAttribute="aAccount">
  <form:input path="accountNumber" type="hidden" id="aAccountNumber" value="<%=accountNo %>"/> 
  <form:input path="pin" type="hidden" id="aPin" value="<%=pin %>"/> 
  
  
<div class="container">
  <div class="btn0">
  	<input type = "button" class="number-button" value = "0"> 
  </div>
  <div class="btn1">
  	<input type="button" class="number-button" value = "1">  
  </div>
  <div class="btn2">
  	<input type = "button" class="number-button" value = "2">  
  </div>
  <div class="btn3">
  	<input type = "button" class="number-button" value = "3"> 
  </div>
  <div class="btn4">
  	<input type = "button" class="number-button" value = "4">
  </div>
  <div class="btn5">
  	<input type = "button" class="number-button" value = "5">  
  </div>
  <div class="btn6">
  	<input type = "button" class="number-button" value = "6"">  
  </div>
  <div class="btn7">
  	<input type = "button" class="number-button" value = "7">  
  </div>
  <div class="btn8">
  	<input type = "button" class="number-button" value = "8">  
  </div>
  <div class="btn9">
  	<input type = "button" class="number-button" value = "9">  
  </div>
  <div class="cancel">
  	<input type="button" class="menu-button" value="cancel" id="cancel" onclick="cancelBtn();">
  </div>
  <div class="clear">
  	<input type="button" class="menu-button" value="clear" id="clear">   
  </div>
  <div class="confirm">
  	<input type = "button" value = "confirm" id= "confirm">  
  </div>
  <div class="screen">
  	<input type="text" id="display">
  </div>
  
  <div class="option1" id="option" onclick="checkbalance();">
  </div>
  <div class="option2" id="option" onclick="withdraw();">
  </div>
  <div class="card1">
  	
  </div>
  <div class="card2">
  	
  </div>
  <div class="screenopt1">
  	<label id="balchkmessage">Balance Check &nbsp;</label>
  </div>
  <div class="screenopt2">
  	<label id="withdrawmessage">Withdrawal  &nbsp;</label>
  </div>
  <div class="screentext">
    <div class="screentextoutput">
    	<label id="screenmessage">Select a Service</label>
    </div>
    <div class="pinarea">
    	<label id="pinmessage"></label>
    </div>
  </div>
</div>
 
</form:form>  
</div>  
</body>  
</html>  