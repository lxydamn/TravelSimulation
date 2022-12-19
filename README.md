# TravelSimulation

## API

### user

```
/api/user/token/  post
/api/user/register/ post
/api/user/info/ get
```

### back

```
/api/back/users/ get

/api/back/addcity/ post
/api/back/getcities/ get
/api/back/deletecity/ post


/api/back/addpath/ post
/api/back/getpaths/ get
/api/back/deletepath/ post
/api/back/modifypath/ post
```

## 数据库

### user

```
create table travelsimulation.user
(
    id       int auto_increment
        primary key,
    username varchar(50)   not null,
    password varchar(5000) not null,
    photo    varchar(1000) not null
);
```

### city

```
create table travelsimulation.city
(
    city_adcode int           not null
        primary key,
    city_name   varchar(50)   not null,
    grade       int default 0 not null,
    constraint city_adcode
        unique (city_adcode)
);
```

#### city数据

```
insert into travelsimulation.city (city_adcode, city_name, grade)
values  (110100, '北京市', 2),
        (120100, '天津市', 0),
        (130100, '石家庄市', 0),
        (140100, '太原市', 0),
        (150100, '呼和浩特市', 0),
        (210100, '沈阳市', 0),
        (220100, '长春市', 1),
        (230100, '哈尔滨市', 0),
        (310100, '上海市', 0),
        (320100, '南京市', 0),
        (330100, '杭州市', 0),
        (340100, '合肥市', 0),
        (350100, '福州市', 0),
        (360100, '南昌市', 0),
        (370100, '济南市', 0),
        (410100, '郑州市', 0),
        (420100, '武汉市', 0),
        (430100, '长沙市', 0),
        (440100, '广州市', 0),
        (450100, '南宁市', 0),
        (460100, '海口市', 0),
        (500100, '重庆市', 0),
        (510100, '成都市', 0),
        (520100, '贵阳市', 0),
        (530100, '昆明市', 0),
        (540100, '拉萨市', 0),
        (610100, '西安市', 0),
        (620100, '兰州市', 0),
        (630100, '西宁市', 0),
        (640100, '银川市', 0),
        (650100, '乌鲁木齐市', 0),
        (710100, '台北市', 0),
        (810000, '香港特别行政区', 0),
        (820000, '澳门特别行政区', 0);
```

### plan

```
create table travelsimulation.plan
(
    user_id     int           not null,
    id          int auto_increment
        primary key,
    content     varchar(3000) not null,
    create_time datetime      not null,
    constraint id
        unique (id)
);
```

#### path

```
create table travelsimulation.path
(
    id         int auto_increment
        primary key,
    start_city int      not null,
    end_city   int      not null,
    start_time datetime not null,
    end_time   datetime not null,
    cost       int      not null,
    type       int      not null,
    constraint id
        unique (id)
);
```
