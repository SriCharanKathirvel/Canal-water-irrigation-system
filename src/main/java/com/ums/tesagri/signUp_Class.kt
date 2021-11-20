package com.ums.tesagri

class signUp_Class(var uid:String,var name:String,var email:String,var password:String,var area:String,var totalacres:String,var landno:String,var serialno:String,var age:String,var timings:String)
{
    constructor() : this("","","","","","","","","","")
}
class RequestClass(var uid:String,var name: String,var totalacres: String,var timings: String)
{
    constructor() : this("","","","")
}
class MotorStatus(var name:String,var time:String,var status:String,var uid: String)
{
    constructor() : this("","","","")
}
class RequestReceive(var name:String,var time:String,var acres:String,var uid: String)
{
    constructor() : this("","","","")
}