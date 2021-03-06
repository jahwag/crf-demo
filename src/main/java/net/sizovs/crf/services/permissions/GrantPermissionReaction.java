package net.sizovs.crf.services.permissions;

import net.sizovs.crf.backbone.Command;
import net.sizovs.crf.backbone.Reaction;
import net.sizovs.crf.services.membership.Member;
import net.sizovs.crf.services.membership.Members;
import org.springframework.stereotype.Component;

@Component
class GrantPermissionReaction implements Reaction<GrantPermission, Command.R.Void> {

    private final Members members;
    private final Permissions permissions;

    public GrantPermissionReaction(Members members, Permissions permissions) {
        this.members = members;
        this.permissions = permissions;
    }

    @Override
    public Command.R.Void react(GrantPermission $) {
        Member member = members.findOne($.memberId());

        Permission permission = permissions.findOne($.permissionId())
                .orElseThrow(() -> new IllegalArgumentException("Cannot find permission (" + $.permissionId() + ")"));

        member.grant(permission);
        return new Command.R.Void();
    }
}
