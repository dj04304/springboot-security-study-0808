 let oEditors = [];
    nhn.husky.EZCreator.createInIFrame({
     oAppRef: oEditors,
     elPlaceHolder: "ir1",
     sSkinURI: "/static/SmartEditor2Skin.html",
     fCreator: "createSEditor2"
});

        
const submitButton = document.querySelector(".submit");

submitButton.onclick = () => {
    /*
        editor의 내용을 textarea로 옮겨주는 역할
        옮기고나서 객체를 불러와야 하기 때문에 oEditors 이후에 선언
    */
    oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);
    const textareaValue = document.querySelector("#ir1").value;
    alert(textareaValue);
    
    let formData = new FormData(document.querySelector("form"));
    
    formData.append("userCode", getUser().user_code); //formData 내에 강제로 user_code를 추가해준다.
    
    formData.forEach((v, k) => {
	console.log("key: " + k);
	console.log("value: " + v);
	});
    
    $.ajax({
	 	async: false,
	 	type: "post",
	 	url: "/api/v1/notice",
	 	enctype: "multipart/form-data",
	 	contentType: false,
	 	processData: false,
	 	data: formData,
	 	dataType: "json",
	 	success: (response) => {
			alert(response.data + "번 공지사항 작성 완료");
			location.href = "/notice/detail/" + response.data;
		},
		error: (error) => {
			console.log(error);
		}
		
	});
    
    
    
    
    
}