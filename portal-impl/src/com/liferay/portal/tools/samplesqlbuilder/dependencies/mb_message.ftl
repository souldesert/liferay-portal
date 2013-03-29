<#include "macro.ftl">

insert into MBMessage values ('${mbMessage.uuid}', ${mbMessage.messageId}, ${mbMessage.groupId}, ${mbMessage.companyId}, ${mbMessage.userId}, '${mbMessage.userName}', '${dataFactory.getDateString(mbMessage.createDate)}', '${dataFactory.getDateString(mbMessage.modifiedDate)}', ${mbMessage.classNameId}, ${mbMessage.classPK}, ${mbMessage.categoryId}, ${mbMessage.threadId}, ${mbMessage.rootMessageId}, ${mbMessage.parentMessageId}, '${mbMessage.subject}', '${mbMessage.body}', '${mbMessage.format}', ${mbMessage.anonymous?string}, ${mbMessage.priority}, ${mbMessage.allowPingbacks?string}, ${mbMessage.answer?string}, ${mbMessage.status}, ${mbMessage.statusByUserId}, '${mbMessage.statusByUserName}', '${dataFactory.getDateString(mbMessage.statusDate)}');

<@insertAssetEntry _entry = mbMessage/>