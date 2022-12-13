const table = {
    initData: [],
   	showField: [],
    makeTable: function () {
        this.table = document.createElement('table') //this를 붙여주면 필드를 선언하는거고 this를 붙이지않으면 변수이다.
        this.table.setAttribute('border','1')
        this.makeHead();
        this.makeBody();
        return this.table;
    },
    makeHead: function () {
         this.thead = document.createElement('thead');
         this.htr = document.createElement('tr')
         let fields =this.showField //this.initData[0];
         for (let prop of fields){
            let th = document.createElement('th');
            th.innerText = prop.toUpperCase();
            this.htr.append(th);
         }
         this.thead.append(this.htr);
         this.table.append(this.thead);
         return this.thead
    },
	addTitle: function(title){
		let th = document.createElement('th');
		th.innerText = title; //<th>title</th>
		this.htr.append(th);
	},
    makeBody: function () {
        let tbody = document.createElement('tbody');
		let sfield = this.showField
        this.initData.forEach((item) => {
            // item :{name:"홍길동",age:20}
            let tr = document.createElement('tr');
            for(let prop of sfield){
                let td = document.createElement('td');
                td.innerText = item[prop];
                tr.append(td);
            }
            tbody.append(tr);
        });
        
        this.table.append(tbody);
    },
    makeTr: function () {
        
    }

}