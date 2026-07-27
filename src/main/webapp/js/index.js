const slides = document.querySelector(".slides");
const images = document.querySelectorAll(".slides img");

const next = document.querySelector(".next");
const prev = document.querySelector(".prev");

const dotsContainer = document.querySelector(".dots");

let index = 0;

/* Tạo các chấm */

images.forEach((img, i)=>{

    const dot = document.createElement("span");
    dot.classList.add("dot");

    if(i==0)
        dot.classList.add("active");

    dot.onclick=()=>{

        index=i;
        update();

    }

    dotsContainer.appendChild(dot);

});

const dots=document.querySelectorAll(".dot");

/* Cập nhật */

function update(){

    slides.style.transform=`translateX(-${index*100}%)`;

    dots.forEach(dot=>dot.classList.remove("active"));

    dots[index].classList.add("active");

}

/* Next */

next.onclick=()=>{

    index++;

    if(index>=images.length)
        index=0;

    update();

}

/* Prev */

prev.onclick=()=>{

    index--;

    if(index<0)
        index=images.length-1;

    update();

}

/* Auto */

setInterval(()=>{

    index++;

    if(index>=images.length)
        index=0;

    update();

},5000);